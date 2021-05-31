package com.hongjun.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hongjun.app.service.UmsUserInfoCacheService;
import com.hongjun.app.service.UmsUserInfoService;
import com.hongjun.app.service.model.UmsLoginModel;
import com.hongjun.app.util.SendOtpCodeUtil;
import com.hongjun.app.vo.UmsUserInfoDetails;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.util.RedisUtil;
import com.hongjun.dataobject.UmsUserInfoDO;
import com.hongjun.dataobject.UmsUserInfoDOExample;
import com.hongjun.mapper.UmsUserInfoDOMapper;
import com.hongjun.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author hongjun500
 * @date 2021/4/14 16:07
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
@Slf4j
public class UmsUserInfoServiceImpl implements UmsUserInfoService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsUserInfoDOMapper UmsUserInfoDOMapper;
    @Autowired
    private UmsUserInfoCacheService umsUserInfoCacheService;


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    @Value("${redis.database.name}")
    private String REDIS_DATABASE;

    @Value("${redis.database.key.otpCode}")
    private String REDIS_DATABASE_KEY_OTPCODE;


    @Override
    public UmsUserInfoDO getByUsername(String username) {
        UmsUserInfoDO member = umsUserInfoCacheService.getMember(username);
        if(member != null) {
            return member;
        }
        UmsUserInfoDOExample example = new UmsUserInfoDOExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsUserInfoDO> memberList = UmsUserInfoDOMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            member = memberList.get(0);
            umsUserInfoCacheService.setMember(member);
            return member;
        }
        return null;
    }

    @Override
    public UmsUserInfoDO getById(Long id) {
        return UmsUserInfoDOMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UmsLoginModel umsLoginModel) throws BusinessException {
        //验证验证码
        String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_OTPCODE + ":" + "register"  + ":" + umsLoginModel.getUsername();
        // 不一致
        if(verifyAuthCode(umsLoginModel.getOtpCode(), key)){
            throw new BusinessException(EnumBusinessError.VERIFY_CODE_NOT_ERROR);
        }
        //根据用户名(目前只有手机号即用户名的清空)查询是否已有该用户
        UmsUserInfoDOExample example = new UmsUserInfoDOExample();
        example.createCriteria().andUsernameEqualTo(umsLoginModel.getUsername());
        List<UmsUserInfoDO> umsMembers = UmsUserInfoDOMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsMembers)) {
            throw new BusinessException(EnumBusinessError.USER_USERNAME_REPETITION);
        }
        //没有该用户进行添加操作
        UmsUserInfoDO umsMember = new UmsUserInfoDO();
        // 账号同手机号
        umsMember.setUsername(umsLoginModel.getUsername());
        umsMember.setNickname(umsLoginModel.getNickname() == null?"":umsLoginModel.getNickname());
        umsMember.setPhone(umsLoginModel.getUsername());
        umsMember.setPassword(passwordEncoder.encode(umsLoginModel.getPassword()));
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);
        UmsUserInfoDOMapper.insertSelective(umsMember);
    }

    /**
     * 对输入的验证码进行是否一致校验
     * @param otpCode
     * @param telephoneKey
     * @return
     */
    private boolean verifyAuthCode(String otpCode, String telephoneKey){
        if(StrUtil.isEmpty(otpCode)){
            return true;
        }
        // 获取redis中的验证码
        String realAuthCode = umsUserInfoCacheService.getOtpCode(telephoneKey);
        // 不一致
        return !otpCode.equals(realAuthCode);
    }

    @Override
    public void generateOtpCode(String telephone, String type) throws BusinessException {
        // 按照一定的规则生成otp验证码
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        // 循环六次
        for(int i = 0;i < 6; i++){
            // 每次生成范围[0-9]
            sb.append(random.nextInt(10));
        }
        log.info("模拟短信平台---{}发送验证码----{}", type, sb);
        log.info("模拟短信平台---{}发送验证码----{}", type, sb);
        log.info("模拟短信平台---{}发送验证码----{}", type, sb);
        // 规范redis业务类型key
        String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_OTPCODE + ":" + type  + ":";
        umsUserInfoCacheService.setOtpCode(key + telephone, sb.toString());
    }

    @Override
    public void updatePassword(UmsLoginModel umsMemberLoginModel) throws BusinessException {
        String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_OTPCODE + ":" + "resetPassword"  + ":" + umsMemberLoginModel.getUsername();
        // 不一致
        if (verifyAuthCode(umsMemberLoginModel.getOtpCode(), key)) {
            throw new BusinessException(EnumBusinessError.VERIFY_CODE_NOT_ERROR);
        }
        String encryptPassword = passwordEncoder.encode(umsMemberLoginModel.getPassword());
        UmsUserInfoDO UmsUserInfoDO = new UmsUserInfoDO();
        UmsUserInfoDO.setPassword(encryptPassword);
        UmsUserInfoDOExample example = new UmsUserInfoDOExample();
        example.createCriteria().andUsernameEqualTo(umsMemberLoginModel.getUsername());
        UmsUserInfoDOMapper.updateByExampleSelective(UmsUserInfoDO, example);
        // 删除缓存中的数据
        umsUserInfoCacheService.delMember(null, umsMemberLoginModel.getUsername());
    }


    @Override
    public UmsUserInfoDO getCurrentMember() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        UmsUserInfoDetails memberDetails = (UmsUserInfoDetails) auth.getPrincipal();
        return memberDetails.getUmsMember();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws BusinessException {
        UmsUserInfoDO member = getByUsername(username);
        if(member!= null){
            return new UmsUserInfoDetails(member);
        }
        throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
    }

    @Override
    public String login(String loginType, String username, String password, String otpCode, HttpServletRequest request, HttpServletResponse response) throws BusinessException, UnsupportedEncodingException {
        String token;
        UserDetails userDetails  = loadUserByUsername(username);
        switch (loginType) {
            case "byPassword":
                // 密码登录
                if(!passwordEncoder.matches(password,userDetails.getPassword())){
                    throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
                }

                break;
            case "byOtpCode":
                // 验证码登录
                // 规范redis业务类型key
                String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_OTPCODE + ":" + "login"  + ":" + username;
                // 不一致
                if (verifyAuthCode(otpCode, key)){
                    throw new BusinessException(EnumBusinessError.VERIFY_CODE_NOT_ERROR);
                }
                break;
            default:
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        token = jwtTokenUtil.generateToken(userDetails);
        // 放入Cookie
        // 用于前后端未分离时页面跳转需要携带授权认证信息
        Cookie cookie = new Cookie(tokenHeader, URLEncoder.encode(tokenHead + token, "UTF-8"));
        // 存一天
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/app");
        response.addCookie(cookie);
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

}
