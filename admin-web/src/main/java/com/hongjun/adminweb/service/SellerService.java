package com.hongjun.adminweb.service;

import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.SellerDO;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/1 23:40
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface SellerService {
    void createSeller(SellerDO sellerDO);
    SellerDO getSellerById(Integer id);
    List<SellerDO> listAll(Integer pageNum, Integer pageSize);
    SellerDO changeStatus(Integer id, Integer disabledFlag) throws BusinessException;
}
