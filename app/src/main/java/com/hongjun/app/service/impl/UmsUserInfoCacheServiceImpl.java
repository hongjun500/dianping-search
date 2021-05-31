package com.hongjun.app.service.impl;

import com.hongjun.app.service.UmsUserInfoCacheService;
import com.hongjun.common.util.RedisUtil;
import com.hongjun.dataobject.UmsUserInfoDO;
import com.hongjun.dataobject.UmsUserInfoDOExample;
import com.hongjun.mapper.UmsUserInfoDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/4/14 16:27
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class UmsUserInfoCacheServiceImpl implements UmsUserInfoCacheService {

    @Value("${redis.database.name}")
    private String REDIS_DATABASE;
    @Value("${redis.database.key.expire.common}")
    private Long REDIS_DATABASE_KEY_EXPIRE_COMMON;
    @Value("${redis.database.key.expire.otpCode}")
    private Long REDIS_DATABASE_KEY_EXPIRE_OTPCODE;
    @Value("${redis.database.key.member}")
    private String REDIS_DATABASE_KEY_MEMBER;
    @Value("${redis.database.key.otpCode}")
    private String REDIS_DATABASE_KEY_OTP_CODE;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UmsUserInfoDOMapper umsUserInfoDOMapper;

    @Override
    public void delMember(Long memberId, String username) {
        UmsUserInfoDO UmsUserInfoDO = null;
        if (memberId != null) {
            UmsUserInfoDO = umsUserInfoDOMapper.selectByPrimaryKey(memberId);
        }
        if (username != null) {
            UmsUserInfoDOExample example = new UmsUserInfoDOExample();
            example.createCriteria().andUsernameEqualTo(username);
            List<UmsUserInfoDO> list = umsUserInfoDOMapper.selectByExample(example);
            if (!list.isEmpty()) {
                UmsUserInfoDO = list.get(0);
            }
        }
        if (UmsUserInfoDO != null) {
            String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_MEMBER + ":" + UmsUserInfoDO.getUsername();
            redisUtil.del(key);
        }
    }

    @Override
    public UmsUserInfoDO getMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_MEMBER + ":" + username;
        return (UmsUserInfoDO) redisUtil.get(key);
    }

    @Override
    public void setMember(UmsUserInfoDO member) {
        String key = REDIS_DATABASE + ":" + REDIS_DATABASE_KEY_MEMBER + ":" + member.getUsername();
        redisUtil.set(key, member, REDIS_DATABASE_KEY_EXPIRE_COMMON);
    }

    @Override
    public void setOtpCode(String telephoneKey, String otpCode) {
        redisUtil.set(telephoneKey, otpCode, REDIS_DATABASE_KEY_EXPIRE_OTPCODE);
    }

    @Override
    public String getOtpCode(String telephoneKey) {
        return (String) redisUtil.get(telephoneKey);
    }
}
