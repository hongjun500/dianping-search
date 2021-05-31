package com.hongjun.adminweb.cache;

import cn.hutool.core.collection.CollUtil;
import com.hongjun.adminweb.service.SysAdminService;
import com.hongjun.common.util.RedisUtil;
import com.hongjun.dataobject.SysAdminDO;
import com.hongjun.dataobject.SysAdminRoleRelationDO;
import com.hongjun.dataobject.SysAdminRoleRelationDOExample;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.mapper.SysAdminRoleRelationDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户缓存类
 */
@Component
public class SysAdminCache {
    @Autowired
    private SysAdminService sysAdminService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysAdminRoleRelationDOMapper sysAdminRoleRelationDOMapper;

    @Value("${spring.redis.database}")
    private String REDIS_DATABASE;
    @Value("${spring.redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${spring.redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${spring.redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    /**
     * 删除缓存中的用户
     * @param adminId 用户Id
     */
    public void delAdmin(Long adminId) {
        SysAdminDO sysAdminDO = sysAdminService.getSysAdminDOById(adminId);
        if (sysAdminDO != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + sysAdminDO.getUsername();
            redisUtil.del(key);
        }
    }

    /**
     * 根据用户id删除缓存中的资源
     * @param adminId
     */
    public void delResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisUtil.del(key);
    }

    /**
     * 根据角色id删除缓存中的资源
     * @param roleId
     */
    public void delResourceListByRole(Long roleId) {
        List<SysAdminRoleRelationDO> relationList = sysAdminRoleRelationDOMapper.selectByRoleId(roleId);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisUtil.del(keys);
        }
    }

    /**
     * 根据角色id列表删除资源
     * @param roleIds
     */
    public void delResourceListByRoleIds(List<Long> roleIds) {
        SysAdminRoleRelationDOExample example = new SysAdminRoleRelationDOExample();
        example.createCriteria().andRoleIdIn(roleIds);
        List<SysAdminRoleRelationDO> relationList = sysAdminRoleRelationDOMapper.selectByRoleIds(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisUtil.del(keys);
        }
    }

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = sysAdminRoleRelationDOMapper.selectByResourceId(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisUtil.del(keys);
        }
    }

    /**
     * 根据用户名从缓存中获取资源
     * @param username
     * @return
     */
    public SysAdminDO getSysAdminDO(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (SysAdminDO) redisUtil.get(key);
    }

    public void setAdmin(SysAdminDO admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisUtil.set(key, admin, REDIS_EXPIRE);
    }

    public List<SysResourceDO> listResourceByAdminId(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<SysResourceDO>) redisUtil.get(key);
    }

    public void setResourceList(Long adminId, List<SysResourceDO> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisUtil.set(key, resourceList, REDIS_EXPIRE);
    }
}
