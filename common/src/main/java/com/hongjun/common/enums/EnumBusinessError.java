package com.hongjun.common.enums;

import com.hongjun.common.error.CommonError;

/**
 * @author hongjun500
 * @date 2020/12/24 22:50
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public enum  EnumBusinessError implements CommonError {

    // 通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    // 20000开头为用户错误信息相关定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户名或密码不正确!"),
    USER_NOT_LOGIN(20003, "用户未登录!"),
    USER_LOGIN_IS_BAN(20004, "账号已禁用!"),
    USER_NOT_ACCESS(20005, "无权访问!"),
    USER_USERNAME_REPETITION(20005, "用户名重复!"),

    // 30000开头为交易错误信息相关定义
    STOCK_NOT_ENOUGH(30001, "库存不足!"),

    // 40000开头为商品信息不存在
    ITEM_NOT_EXIST(40001, "商品不存在!"),

    // 50000开头为验证码不正确
    VERIFY_CODE_NOT_ERROR(50001, "验证码不正确!"),
    VERIFY_CODE_PAST(50002, "验证码已过期!"),

    // 60000开头为token相关
    TOKEN_PAST(60001, "token已过期!"),

    // 70001开头redis相关
    REDIS_CONNECTION_ERROR(70001,"redis缓存连接失败!")
    ;

    // 枚举可以拥有私有成员变量，因为本身就是一个面向对象的类(枚举类)
    private int errCode;
    private String errMsg;

    EnumBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
