package com.hongjun.mapper;

import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysResourceDOExample;

import java.util.List;
public interface SysResourceDOMapper {
    long countByExample(SysResourceDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysResourceDO record);

    int insertSelective(SysResourceDO record);

    SysResourceDO selectByPrimaryKey(Long id);

    List<SysResourceDO> selectByExample(SysResourceDOExample example);

    List<SysResourceDO> selectSysResourceAll(SysResourceDOExample example);

    int updateByPrimaryKeySelective(SysResourceDO record);

    int updateByPrimaryKey(SysResourceDO record);

}