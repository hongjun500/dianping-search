/**
 * @description:
 * @author: zxz
 * @create: 2021-05-26 09:26
 **/
package com.hongjun.app.dao;

import com.hongjun.dataobject.UmsUserInfoDO;

public interface UmsMemUserInfoDao {
    Integer updateusersign(UmsUserInfoDO UmsUserInfoDO);

    Integer updateUserInfo(UmsUserInfoDO UmsUserInfoDO);
}
