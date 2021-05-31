package com.hongjun.adminweb.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.bo.SysAdminUserDetails;
import com.hongjun.adminweb.cache.SysAdminCache;
import com.hongjun.adminweb.service.SysAdminService;
import com.hongjun.adminweb.service.model.SysAdminModel;
import com.hongjun.adminweb.service.model.SysAdminPasswordModel;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.*;
import com.hongjun.mapper.SysAdminDOMapper;
import com.hongjun.mapper.SysAdminRoleRelationDOMapper;
import com.hongjun.mapper.SysResourceDOMapper;
import com.hongjun.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/22 17:59
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
@Slf4j
public class SysAdminServiceImpl implements SysAdminService {



    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private SysAdminCache sysAdminCache;
    @Autowired
    private SysAdminDOMapper sysAdminDOMapper;
    @Autowired
    private SysAdminRoleRelationDOMapper sysAdminRoleRelationDOMapper;

    @Override
    public SysAdminDO getAdminByUsername(String username) {
        // 先从缓存中读取
        SysAdminDO sysAdminDO = sysAdminCache.getSysAdminDO(username);
        if (sysAdminDO != null){
            return sysAdminDO;
        }
        // 没有就查数据库
        SysAdminDOExample example = new SysAdminDOExample();
        example.createCriteria().andUsernameEqualTo(username);
        SysAdminDO dataSysAdminDO = sysAdminDOMapper.selectByUsername(username);
        if (dataSysAdminDO != null){
            // 放入缓存
            sysAdminCache.setAdmin(dataSysAdminDO);
            return dataSysAdminDO;
        }
        return null;
    }

    @Override
    public SysAdminDO register(SysAdminModel sysAdminModel) throws BusinessException {
        SysAdminDO sysAdminDO = new SysAdminDO();
        BeanUtils.copyProperties(sysAdminModel, sysAdminDO);
        sysAdminDO.setCreateTime(new Date());
        // sysAdminDO.setStatus(1);
        //查询用户名是否重复
        SysAdminDO data = sysAdminDOMapper.selectByUsername(sysAdminDO.getUsername());
        if (data != null) {
            throw new BusinessException(EnumBusinessError.USER_USERNAME_REPETITION);
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(sysAdminDO.getPassword());
        sysAdminDO.setPassword(encodePassword);
        try {
            sysAdminDOMapper.insertSelective(sysAdminDO);
        } catch (Exception e) {
            // 给用户名加上唯一索引之后可直接进行写操作不去查询是否重复
            if (e instanceof DuplicateKeyException) {
                throw new BusinessException(EnumBusinessError.USER_USERNAME_REPETITION);
            }
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
        return sysAdminDO;
    }

    @Override
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                // 用户/密码不正确
                throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
            }
            if(!userDetails.isEnabled()){
                // 账号禁用
                throw new BusinessException(EnumBusinessError.USER_LOGIN_IS_BAN);
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            // 放入Cookie
            // 用于前后端未分离时页面跳转需要携带授权认证信息
            Cookie cookie = new Cookie(tokenHeader, URLEncoder.encode(tokenHead + token, "UTF-8"));
            // 存一天
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (AuthenticationException | UnsupportedEncodingException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public SysAdminDO getSysAdminDOById(Long id) {
        return sysAdminDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysAdminDO> list(String keyword, Integer pageSize, Integer pageNum) {
        SysAdminDOExample example = new SysAdminDOExample();
        SysAdminDOExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotBlank(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
        }
        PageHelper.startPage(pageNum, pageSize);
        return sysAdminDOMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, SysAdminDO admin) {
        admin.setId(id);
        SysAdminDO rawAdmin = sysAdminDOMapper.selectByPrimaryKey(id);
        if(rawAdmin.getPassword().equals(admin.getPassword())){
            //与原加密密码相同的不需要修改
            admin.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else{
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        int count = sysAdminDOMapper.updateByPrimaryKeySelective(admin);
        sysAdminCache.delAdmin(id);
        return count;
    }

    @Override
    public int delete(Long id) {
        sysAdminCache.delAdmin(id);
        int count = sysAdminDOMapper.deleteByPrimaryKey(id);
        sysAdminCache.delResourceList(id);
        return count;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        SysAdminRoleRelationDOExample adminRoleRelationExample = new SysAdminRoleRelationDOExample();
        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        sysAdminRoleRelationDOMapper.deleteByExample(adminRoleRelationExample);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<SysAdminRoleRelationDO> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                SysAdminRoleRelationDO roleRelation = new SysAdminRoleRelationDO();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            sysAdminRoleRelationDOMapper.insertList(list);
        }
        sysAdminCache.delResourceList(adminId);
        return count;
    }

    @Override
    public List<SysRoleDO> listRoleByAdminId(Long adminId) {
        return sysAdminRoleRelationDOMapper.selectRoleByAdminId(adminId);
    }

    @Override
    public List<SysResourceDO> getResourceList(Long adminId) {
        List<SysResourceDO> resourceList = sysAdminCache.listResourceByAdminId(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }
        resourceList = sysAdminRoleRelationDOMapper.listResourcesByAdminId(adminId);
        if(CollUtil.isNotEmpty(resourceList)){
            sysAdminCache.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(SysAdminPasswordModel updatePasswordParam) {
        if(StrUtil.isNotBlank(updatePasswordParam.getUsername())
                ||StrUtil.isNotBlank(updatePasswordParam.getOldPassword())
                ||StrUtil.isNotBlank(updatePasswordParam.getNewPassword())){
            return -1;
        }
        SysAdminDOExample example = new SysAdminDOExample();
        example.createCriteria().andUsernameEqualTo(updatePasswordParam.getUsername());
        List<SysAdminDO> adminList = sysAdminDOMapper.selectByExample(example);
        if(CollUtil.isEmpty(adminList)){
            return -2;
        }
        SysAdminDO sysAdminDO = adminList.get(0);
        if(!passwordEncoder.matches(updatePasswordParam.getOldPassword(),updatePasswordParam.getNewPassword())){
            return -3;
        }
        sysAdminDO.setPassword(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
        sysAdminDOMapper.updateByPrimaryKey(sysAdminDO);
        sysAdminCache.delAdmin(sysAdminDO.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws BusinessException {
        // 获取用户信息
            SysAdminDO admin = getAdminByUsername(username);
        if (admin != null) {
            List<SysResourceDO> resourceList = getResourceList(admin.getId());
            return new SysAdminUserDetails(admin,resourceList);
        }
        throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL,"用户名或密码错误!");
    }
}
