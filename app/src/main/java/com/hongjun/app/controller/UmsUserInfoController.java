package com.hongjun.app.controller;

import cn.hutool.core.util.StrUtil;
import com.hongjun.app.service.UmsUserInfoCacheService;
import com.hongjun.app.service.UmsUserInfoService;
import com.hongjun.app.service.model.UmsLoginModel;
import com.hongjun.app.valator.ValidatorUtil;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidationResult;
import com.hongjun.dataobject.SysAdminDO;
import com.hongjun.dataobject.UmsUserInfoDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2021/4/14 16:05
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 会员信息控制器
 */
@RequestMapping(value = "/app/ums")
@RestController
@Api(tags = "app用户相关接口")
public class UmsUserInfoController extends BaseController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private UmsUserInfoService umsUserInfoService;

    @Autowired
    private UmsUserInfoCacheService umsUserInfoCacheService;

    @ApiOperation("获取验证码")
    @GetMapping(value = "/getOtpCode")
    public CommonReturnType<Object> getOtpCode(@RequestParam String telephone, @ApiParam(value = "获取验证码类型，注册register,登录login,改密码resetPassword") @RequestParam String type) throws BusinessException {
        if (StrUtil.isBlank(telephone) || StrUtil.isBlank(type)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        umsUserInfoService.generateOtpCode(telephone, type);
        return CommonReturnType.create(null);
    }

    @ApiOperation("会员注册")
    @PostMapping(value = "/register")
    public CommonReturnType<Object> register(UmsLoginModel umsMemberLoginModel) throws BusinessException {
        ValidationResult validationResult = validatorUtil.validate(umsMemberLoginModel);
        if (validationResult.getHasErrors()) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, validationResult.getErrorMsg());
        }
        umsUserInfoService.register(umsMemberLoginModel);
        return CommonReturnType.create(null);
    }

    @ApiOperation("会员登录")
    @PostMapping(value = "/login")
    public CommonReturnType login(@RequestParam @ApiParam(value = "密码byPassword/验证码byOtpCode") String loginType, @RequestParam String username,
                                  @RequestParam(required = false) String password,
                                  @RequestParam(required = false) @ApiParam(value = "验证码")String otpCode,
                                    HttpServletRequest request, HttpServletResponse response) throws BusinessException, UnsupportedEncodingException {

        String token = umsUserInfoService.login(loginType, username, password, otpCode,request, response);
        /*if (token == null) {
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        }*/
        Map<String, String> tokenMap = new HashMap<>(16);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonReturnType.create(tokenMap);
    }

    @ApiOperation("会员退出登录")
    @PostMapping(value = "/logout")
    public CommonReturnType<Object> logout(Principal principal, HttpServletResponse response) throws BusinessException {
        // 清空Cookie
        Cookie cookie = new Cookie(tokenHeader, null);
        cookie.setMaxAge(0);
        cookie.setPath("/app");
        response.addCookie(cookie);
        // 删除缓存
        if(principal==null){
            return CommonReturnType.create(null);
            // throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN);
        }
        UmsUserInfoDO currentMember = umsUserInfoService.getCurrentMember();
        umsUserInfoCacheService.delMember(currentMember.getId(), null);
        return CommonReturnType.create(null);
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public CommonReturnType refreshToken(HttpServletRequest request) throws BusinessException {
        String token = request.getHeader(tokenHeader);
        String refreshToken = umsUserInfoService.refreshToken(token);
        if (refreshToken == null) {
            throw new BusinessException(EnumBusinessError.TOKEN_PAST);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonReturnType.create(tokenMap);
    }

    @ApiOperation("获取会员信息")
    @GetMapping(value = "/info")
    @ApiImplicitParam(name = "name",value = "当前用户相关信息，不用传参")
    public CommonReturnType info(Principal principal) throws BusinessException {
        if (principal == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN);
        }
        UmsUserInfoDO member = umsUserInfoService.getCurrentMember();
        return CommonReturnType.create(member);
    }

    @ApiOperation("通过验证码修改密码")
    @PostMapping(value = "/resetPassword")
    public CommonReturnType<Object> resetPassword(UmsLoginModel umsMemberLoginModel) throws BusinessException {
        ValidationResult validate = validatorUtil.validate(umsMemberLoginModel);
        if (validate.getHasErrors()) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, validate.getErrorMsg());
        }
        umsUserInfoService.updatePassword(umsMemberLoginModel);
        return CommonReturnType.create(null);
    }
}
