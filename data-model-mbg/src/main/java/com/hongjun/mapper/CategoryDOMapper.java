package com.hongjun.mapper;

import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.CategoryDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryDOMapper {
    long countByExample(CategoryDOExample example);

    int deleteByExample(CategoryDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CategoryDO record);

    int insertSelective(CategoryDO record);

    List<CategoryDO> selectByExample(CategoryDOExample example);

    CategoryDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategoryDO record, @Param("example") CategoryDOExample example);

    int updateByExample(@Param("record") CategoryDO record, @Param("example") CategoryDOExample example);

    int updateByPrimaryKeySelective(CategoryDO record);

    int updateByPrimaryKey(CategoryDO record);
}