package com.hongjun.mapper;

import com.hongjun.dataobject.SysAdminDO;
import com.hongjun.dataobject.SysAdminDOExample;

import java.util.List;

public interface SysAdminDOMapper {
    long countByExample(SysAdminDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysAdminDO record);

    int insertSelective(SysAdminDO record);

    SysAdminDO selectByPrimaryKey(Long id);
    SysAdminDO selectByUsername(String username);

    List<SysAdminDO> selectByExample(SysAdminDOExample example);

    int updateByPrimaryKeySelective(SysAdminDO record);

    int updateByPrimaryKey(SysAdminDO record);
}