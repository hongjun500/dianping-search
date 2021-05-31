package com.hongjun.mapper;

import com.hongjun.dataobject.SysRolePermissionRelationDO;
import com.hongjun.dataobject.SysRolePermissionRelationDOExample;

public interface SysRolePermissionRelationDOMapper {
    long countByExample(SysRolePermissionRelationDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermissionRelationDO record);

    int insertSelective(SysRolePermissionRelationDO record);

    SysRolePermissionRelationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermissionRelationDO record);

    int updateByPrimaryKey(SysRolePermissionRelationDO record);
}