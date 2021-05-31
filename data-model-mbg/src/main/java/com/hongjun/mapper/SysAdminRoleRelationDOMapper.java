package com.hongjun.mapper;

import com.hongjun.dataobject.SysAdminRoleRelationDO;
import com.hongjun.dataobject.SysAdminRoleRelationDOExample;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAdminRoleRelationDOMapper {
    long countByExample(SysAdminRoleRelationDOExample example);

    int deleteByPrimaryKey(Long id);

    int deleteByExample(SysAdminRoleRelationDOExample example);

    int insert(SysAdminRoleRelationDO record);

    int insertSelective(SysAdminRoleRelationDO record);

    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<SysAdminRoleRelationDO> adminRoleRelationList);

    SysAdminRoleRelationDO selectByPrimaryKey(Long id);
    List<SysAdminRoleRelationDO> selectByRoleId(Long id);
    List<SysAdminRoleRelationDO> selectByRoleIds(SysAdminRoleRelationDOExample example);
    /**
     * 获取资源相关用户ID列表
     */
    List<Long> selectByResourceId(@Param("resourceId") Long resourceId);

    /**
     * 根据adminId获取所有角色
     * @param adminId
     * @return
     */
    List<SysRoleDO> selectRoleByAdminId(@Param("adminId") Long adminId);

    /**
     * 获取用户所有可访问资源
     */
    List<SysResourceDO> listResourcesByAdminId(@Param("adminId") Long adminId);

    int updateByPrimaryKeySelective(SysAdminRoleRelationDO record);

    int updateByPrimaryKey(SysAdminRoleRelationDO record);
}