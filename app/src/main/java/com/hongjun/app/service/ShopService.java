package com.hongjun.app.service;

import com.hongjun.app.service.model.ShopModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/5 17:20
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface ShopService {
    List<ShopModel> recommend(BigDecimal longitude, BigDecimal latitude);
}
