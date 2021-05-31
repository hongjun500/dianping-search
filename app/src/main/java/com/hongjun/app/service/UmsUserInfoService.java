package com.hongjun.app.service;

import com.hongjun.app.service.model.UmsLoginModel;
import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.UmsUserInfoDO;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author hongjun500
 * @date 2021/4/14 16:07
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface UmsUserInfoService {

    /**
     * 根据用户名获取会员
     * @param username 用户名
     * @return
     */
    UmsUserInfoDO getByUsername(String username);

    /**
     * 根据会员id获取会员
     */
    UmsUserInfoDO getById(Long id);

    /**
     * 用户注册
     */
    void register(UmsLoginModel umsLoginModel) throws BusinessException;

    /**
     * 生成验证码
     * @param telephone
     * @param type 获取验证码类型：register,login
     */
    void generateOtpCode(String telephone, String type) throws BusinessException;

    /**
     * 修改密码
     */
    void updatePassword(UmsLoginModel umsLoginModel) throws BusinessException;

    /**
     * 获取当前登录会员
     */
    UmsUserInfoDO getCurrentMember();
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username) throws BusinessException;

    /**
     * 登录后获取token
     * @param username
     * @param loginType byPassword/byOtpCode
     * @param password
     * @param otpCode 六位数验证码
     * @return
     */
    String login(String loginType, String username, String password, String otpCode, HttpServletRequest request, HttpServletResponse response) throws BusinessException, UnsupportedEncodingException;

    /**
     * 刷新token
     */
    String refreshToken(String token);



}
