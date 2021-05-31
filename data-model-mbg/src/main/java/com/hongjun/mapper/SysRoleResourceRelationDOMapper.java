package com.hongjun.mapper;

import com.hongjun.dataobject.SysRoleResourceRelationDO;
import com.hongjun.dataobject.SysRoleResourceRelationDOExample;

public interface SysRoleResourceRelationDOMapper {
    long countByExample(SysRoleResourceRelationDOExample example);

    int deleteByPrimaryKey(Long id);

    int deleteByExample(SysRoleResourceRelationDOExample example);

    int insert(SysRoleResourceRelationDO record);

    int insertSelective(SysRoleResourceRelationDO record);

    SysRoleResourceRelationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRoleResourceRelationDO record);

    int updateByPrimaryKey(SysRoleResourceRelationDO record);
}