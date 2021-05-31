package com.hongjun.adminweb.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author hongjun500
 * @date 2021/3/22 22:04
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Getter
@Setter
public class SysAdminLoginModel {
    @NotNull(message = "用户名不能为空!")
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @NotNull(message = "密码不能为空!")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
