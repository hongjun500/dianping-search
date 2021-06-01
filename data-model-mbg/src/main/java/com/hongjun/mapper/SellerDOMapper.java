package com.hongjun.mapper;

import com.hongjun.dataobject.SellerDO;
import com.hongjun.dataobject.SellerDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellerDOMapper {
    long countByExample(SellerDOExample example);

    int deleteByExample(SellerDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SellerDO record);

    int insertSelective(SellerDO record);

    List<SellerDO> selectByExample(SellerDOExample example);

    SellerDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SellerDO record, @Param("example") SellerDOExample example);

    int updateByExample(@Param("record") SellerDO record, @Param("example") SellerDOExample example);

    int updateByPrimaryKeySelective(SellerDO record);

    int updateByPrimaryKey(SellerDO record);
}