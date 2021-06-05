package com.hongjun.app.service.model;

import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.SellerDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hongjun500
 * @date 2021/6/5 16:17
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Data
public class ShopModel {
    private Integer id;

    private Date createdAt;

    private Date updatedAt;

    private String name;

    private BigDecimal remarkScore;

    private Integer pricePerMan;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer categoryId;

    private String tags;

    private String startTime;

    private String endTime;

    private String address;

    private Integer sellerId;

    private String iconUrl;

    private SellerDO sellerDO;

    private CategoryDO categoryDO;

    private BigDecimal distance;
}
