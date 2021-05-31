package com.hongjun.app.service;

import com.hongjun.dataobject.UmsUserInfoDO;

/**
 * 会员信息缓存业务类
 * @author hongjun500
 */
public interface UmsUserInfoCacheService {
    /**
     * 删除会员用户缓存
     */
    void delMember(Long memberId, String username);

    /**
     * 获取会员用户缓存
     * @param username
     * @return
     */
    UmsUserInfoDO getMember(String username);

    /**
     * 设置会员用户缓存
     */
    void setMember(UmsUserInfoDO member);

    /**
     * 设置验证码
     */
    void setOtpCode(String telephoneKey, String otpCode);

    /**
     * 获取验证码
     */
    String getOtpCode(String telephoneKey);
}
