package com.hongjun.app.service;

import com.hongjun.app.service.model.ShopModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2021/6/5 17:20
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface ShopService {
    List<ShopModel> recommend(BigDecimal longitude, BigDecimal latitude);
    List<ShopModel> search(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderBy, Integer categoryId, String tags);
    List<Map<String, Object>> searchByGroupTags(String keyword, Integer categoryId, String tags);
}
