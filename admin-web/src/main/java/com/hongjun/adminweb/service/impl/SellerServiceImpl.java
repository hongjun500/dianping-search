package com.hongjun.adminweb.service.impl;

import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.service.SellerService;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.SellerDO;
import com.hongjun.dataobject.SellerDOExample;
import com.hongjun.mapper.SellerDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/1 23:40
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDOMapper sellerDOMapper;

    @Override
    public void createSeller(SellerDO sellerDO) {
        sellerDO.setCreatedAt(new Date());
        sellerDO.setUpdatedAt(new Date());
        sellerDO.setRemarkScore(new BigDecimal(0));
        sellerDOMapper.insertSelective(sellerDO);
    }

    @Override
    public SellerDO getSellerById(Integer id) {
        return sellerDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SellerDO> listAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SellerDO> list = sellerDOMapper.selectByExample(new SellerDOExample());
        return list;
    }

    @Override
    public SellerDO changeStatus(Integer id, Integer disabledFlag) throws BusinessException {
        SellerDO sellerModel = getSellerById(id);
        if(sellerModel == null){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        sellerModel.setDisabledFlag(disabledFlag);
        sellerDOMapper.updateByPrimaryKeySelective(sellerModel);
        return sellerModel;
    }
}
