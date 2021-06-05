package com.hongjun.adminweb.service.impl;

import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.service.SellerService;
import com.hongjun.adminweb.service.ShopService;
import com.hongjun.adminweb.service.model.ShopModel;
import com.hongjun.adminweb.service.util.PageQueryUtil;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.*;
import com.hongjun.mapper.CategoryDOMapper;
import com.hongjun.mapper.SellerDOMapper;
import com.hongjun.mapper.ShopDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/3 16:16
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
    private ShopDOMapper shopDOMapper;

    @Override
    public ShopDO createShop(ShopDO shopDO) throws BusinessException {
        shopDO.setCreatedAt(new Date());
        shopDO.setUpdatedAt(new Date());

        // 校验商家信息
        SellerDO sellerDO = sellerDOMapper.selectByPrimaryKey(shopDO.getSellerId());
        if (sellerDO == null){
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, "商家不存在");
        }
        if (sellerDO.getDisabledFlag() == 0){
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, "商户已禁用");
        }
        CategoryDO categoryDO = categoryDOMapper.selectByPrimaryKey(shopDO.getCategoryId());
        if (categoryDO == null){
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, "类目不存在");
        }
        shopDOMapper.insertSelective(shopDO);
        return shopDO;
    }

    @Override
    public ShopModel getById(Integer id) {
        ShopDO shopDO = shopDOMapper.selectByPrimaryKey(id);
        if (shopDO == null) {
            return null;
        }
        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shopDO, shopModel);
        shopModel.setSellerDO(sellerDOMapper.selectByPrimaryKey(shopDO.getSellerId()));
        shopModel.setCategoryDO(categoryDOMapper.selectByPrimaryKey(shopDO.getCategoryId()));
        return shopModel;
    }

    @Override
    public List<ShopModel> listAll(PageQueryUtil queryUtil) {
        ShopDOExample example = new ShopDOExample();
        example.setOrderByClause("id asc");
        PageHelper.startPage(queryUtil.getPageNum(), queryUtil.getPageSize());
        List<ShopDO> shopDOList = shopDOMapper.selectByExample(example);
        List<ShopModel> shopModelList = new ArrayList<>();
        if (shopDOList.isEmpty()){
            return shopModelList;
        }
        List<SellerDO> sellerDOList = sellerDOMapper.selectByExample(new SellerDOExample());
        List<CategoryDO> categoryDOList = categoryDOMapper.selectByExample(new CategoryDOExample());
        shopDOList.forEach(shopDO -> {
            ShopModel shopModel = new ShopModel();
            BeanUtils.copyProperties(shopDO, shopModel);
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
            shopModelList.add(shopModel);
        });
        return shopModelList;
    }
}
