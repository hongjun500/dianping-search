package com.hongjun.mapper;

import com.hongjun.dataobject.SysMenuDO;
import com.hongjun.dataobject.SysMenuDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuDOMapper {
    long countByExample(SysMenuDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysMenuDO record);

    int insertSelective(SysMenuDO record);

    SysMenuDO selectByPrimaryKey(Long id);

    List<SysMenuDO> selectByExample(SysMenuDOExample example);

    /**
     * 根据后台用户ID获取菜单
     */
    List<SysMenuDO> listByAdminId(@Param("adminId") Long adminId);

    int updateByPrimaryKeySelective(SysMenuDO record);

    int updateByPrimaryKey(SysMenuDO record);
}