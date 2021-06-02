package com.hongjun.mapper;

import com.hongjun.dataobject.ShopDO;
import com.hongjun.dataobject.ShopDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopDOMapper {
    long countByExample(ShopDOExample example);

    int deleteByExample(ShopDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShopDO record);

    int insertSelective(ShopDO record);

    List<ShopDO> selectByExample(ShopDOExample example);

    ShopDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShopDO record, @Param("example") ShopDOExample example);

    int updateByExample(@Param("record") ShopDO record, @Param("example") ShopDOExample example);

    int updateByPrimaryKeySelective(ShopDO record);

    int updateByPrimaryKey(ShopDO record);
}