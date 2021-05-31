package com.hongjun.mapper;

import com.hongjun.dataobject.UmsUserInfoDO;
import com.hongjun.dataobject.UmsUserInfoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsUserInfoDOMapper {
    long countByExample(UmsUserInfoDOExample example);

    int deleteByExample(UmsUserInfoDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsUserInfoDO record);

    int insertSelective(UmsUserInfoDO record);

    List<UmsUserInfoDO> selectByExample(UmsUserInfoDOExample example);

    UmsUserInfoDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsUserInfoDO record, @Param("example") UmsUserInfoDOExample example);

    int updateByExample(@Param("record") UmsUserInfoDO record, @Param("example") UmsUserInfoDOExample example);

    int updateByPrimaryKeySelective(UmsUserInfoDO record);

    int updateByPrimaryKey(UmsUserInfoDO record);
}