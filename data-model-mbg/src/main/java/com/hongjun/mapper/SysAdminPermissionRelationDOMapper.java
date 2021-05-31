package com.hongjun.mapper;

import com.hongjun.dataobject.SysAdminPermissionRelationDO;
import com.hongjun.dataobject.SysAdminPermissionRelationDOExample;

public interface SysAdminPermissionRelationDOMapper {
    long countByExample(SysAdminPermissionRelationDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysAdminPermissionRelationDO record);

    int insertSelective(SysAdminPermissionRelationDO record);

    SysAdminPermissionRelationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAdminPermissionRelationDO record);

    int updateByPrimaryKey(SysAdminPermissionRelationDO record);
}