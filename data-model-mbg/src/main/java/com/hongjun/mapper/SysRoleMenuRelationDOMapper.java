package com.hongjun.mapper;

import com.hongjun.dataobject.SysRoleMenuRelationDO;
import com.hongjun.dataobject.SysRoleMenuRelationDOExample;

public interface SysRoleMenuRelationDOMapper {
    long countByExample(SysRoleMenuRelationDOExample example);

    int deleteByPrimaryKey(Long id);

    int deleteByExample(SysRoleMenuRelationDOExample example);

    int insert(SysRoleMenuRelationDO record);

    int insertSelective(SysRoleMenuRelationDO record);

    SysRoleMenuRelationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleMenuRelationDO record);

    int updateByPrimaryKey(SysRoleMenuRelationDO record);
}