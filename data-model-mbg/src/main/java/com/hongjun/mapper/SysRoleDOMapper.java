package com.hongjun.mapper;

import com.hongjun.dataobject.SysMenuDO;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysRoleDO;
import com.hongjun.dataobject.SysRoleDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleDOMapper {
    long countByExample(SysRoleDOExample example);

    int deleteByPrimaryKey(Long id);

    int deleteByExample(SysRoleDOExample example);

    int insert(SysRoleDO record);

    int insertSelective(SysRoleDO record);

    List<SysRoleDO> selectByExample(SysRoleDOExample example);


    SysRoleDO selectByPrimaryKey(Long id);

    /**
     * 根据角色ID获取菜单
     */
    List<SysMenuDO> selectListByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID获取资源
     */
    List<SysResourceDO> selectResourceListByRoleId(@Param("roleId") Long roleId);

    int updateByPrimaryKeySelective(SysRoleDO record);

    int updateByPrimaryKey(SysRoleDO record);
}