package com.hongjun.app.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hongjun500
 * @date 2021/4/15 16:54
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Data
public class UmsLoginModel {
    @NotBlank(message = "用户名/手机号不能为空")
    private String username;
    private String nickname;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "验证码不能为空")
    private String otpCode;
}
