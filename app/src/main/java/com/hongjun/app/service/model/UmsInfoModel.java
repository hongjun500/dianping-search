package com.hongjun.app.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author hongjun500
 * @date 2021/4/12 18:14
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 用户模型
 */
@Data
public class UmsInfoModel {

    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "注册时间")
    private Date createTime;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "性别：0->未知；1->男；2->女")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "用户来源，1001代表员工，1002代表会员")
    private Integer sourceType;

    @ApiModelProperty(value = "积分")
    private Integer integration;

    @ApiModelProperty(value = "签到时间")
    private Integer signTime;

    @ApiModelProperty(value = "连续签到天数")
    private Integer singDays;

    @ApiModelProperty(value = "邀请人id，为null的时候自行注册的")
    private Long parentId;

    @ApiModelProperty(value = "部门id，目前如果不为null(1001)则代表员工")
    private Long departmentId;

    @ApiModelProperty(value = "职位id")
    private Long positionId;
}
