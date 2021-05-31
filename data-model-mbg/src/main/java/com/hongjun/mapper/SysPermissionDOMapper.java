package com.hongjun.mapper;

import com.hongjun.dataobject.SysPermissionDO;
import com.hongjun.dataobject.SysPermissionDOExample;

public interface SysPermissionDOMapper {
    long countByExample(SysPermissionDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysPermissionDO record);

    int insertSelective(SysPermissionDO record);

    SysPermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermissionDO record);

    int updateByPrimaryKey(SysPermissionDO record);
}