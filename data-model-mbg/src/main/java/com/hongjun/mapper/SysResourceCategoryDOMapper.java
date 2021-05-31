package com.hongjun.mapper;

import com.hongjun.dataobject.SysResourceCategoryDO;
import com.hongjun.dataobject.SysResourceCategoryDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysResourceCategoryDOMapper {
    long countByExample(SysResourceCategoryDOExample example);

    int deleteByExample(SysResourceCategoryDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysResourceCategoryDO record);

    int insertSelective(SysResourceCategoryDO record);

    List<SysResourceCategoryDO> selectByExample(SysResourceCategoryDOExample example);

    SysResourceCategoryDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysResourceCategoryDO record, @Param("example") SysResourceCategoryDOExample example);

    int updateByExample(@Param("record") SysResourceCategoryDO record, @Param("example") SysResourceCategoryDOExample example);

    int updateByPrimaryKeySelective(SysResourceCategoryDO record);

    int updateByPrimaryKey(SysResourceCategoryDO record);
}