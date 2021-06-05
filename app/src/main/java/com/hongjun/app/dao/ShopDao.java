package com.hongjun.app.dao;

import com.hongjun.app.service.model.ShopModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2021/6/5 17:33
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface ShopDao {
    List<ShopModel> recommend(@Param(value = "longitude")BigDecimal longitude, @Param(value = "latitude") BigDecimal latitude);
    List<ShopModel> search(@Param(value = "longitude")BigDecimal longitude,
                           @Param(value = "latitude") BigDecimal latitude,
                           @Param(value = "keyword") String keyword,
                           @Param(value = "orderBy")Integer orderBy,
                           @Param(value = "categoryId")Integer categoryId,
                           @Param(value = "tags") String tags);
    List<Map<String, Object>> selectGroupByTags(@Param(value = "keyword") String keyword, @Param(value = "categoryId") Integer categoryId, @Param(value = "tags") String tags);
}
