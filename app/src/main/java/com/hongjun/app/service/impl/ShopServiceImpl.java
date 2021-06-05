package com.hongjun.app.service.impl;

import com.hongjun.app.dao.ShopDao;
import com.hongjun.app.service.ShopService;
import com.hongjun.app.service.model.ShopModel;
import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.CategoryDOExample;
import com.hongjun.dataobject.SellerDO;
import com.hongjun.dataobject.SellerDOExample;
import com.hongjun.mapper.CategoryDOMapper;
import com.hongjun.mapper.SellerDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2021/6/5 17:20
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private SellerDOMapper sellerDOMapper;
    @Autowired
    private CategoryDOMapper categoryDOMapper;

    @Autowired
    private ShopDao shopDao;

    @Override
    public List<ShopModel> recommend(BigDecimal longitude, BigDecimal latitude) {
        List<ShopModel> shopModelList = shopDao.recommend(longitude, latitude);
        if (shopModelList.isEmpty()){
            return shopModelList;
        }
        List<SellerDO> sellerDOList = sellerDOMapper.selectByExample(new SellerDOExample());
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(new CategoryDOExample());
        shopModelList.forEach(shopModel -> {
            sellerDOList.forEach(sellerDO -> {
                if (sellerDO.getId().equals(shopModel.getSellerId())){
                    shopModel.setSellerDO(sellerDO);
                }
            });
            categoryDOList.forEach(categoryDO -> {
                if (categoryDO.getId().equals(shopModel.getCategoryId())){
                    shopModel.setCategoryDO(categoryDO);
                }
            });
        });
        return shopModelList;
    }

    @Override
    public List<ShopModel> search(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderBy, Integer categoryId, String tags){
        List<ShopModel> shopModelList = shopDao.search(longitude, latitude, keyword, orderBy, categoryId, tags);
        if (shopModelList.isEmpty()){
            return new ArrayList<>();
        }
        List<SellerDO> sellerDOList = sellerDOMapper.selectByExample(new SellerDOExample());
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(new CategoryDOExample());
        shopModelList.forEach(shopModel -> {
            sellerDOList.forEach(sellerDO -> {
                if (sellerDO.getId().equals(shopModel.getSellerId())){
                    shopModel.setSellerDO(sellerDO);
                }
            });
            categoryDOList.forEach(categoryDO -> {
                if (categoryDO.getId().equals(shopModel.getCategoryId())){
                    shopModel.setCategoryDO(categoryDO);
                }
            });
        });
        return shopModelList;
    }

    @Override
    public List<Map<String, Object>> searchByGroupTags(String keyword, Integer categoryId, String tags) {
        return shopDao.selectGroupByTags(keyword, categoryId, tags);
    }
}
