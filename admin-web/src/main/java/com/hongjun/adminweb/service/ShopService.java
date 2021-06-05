package com.hongjun.adminweb.service;

import com.hongjun.adminweb.service.model.ShopModel;
import com.hongjun.adminweb.service.util.PageQueryUtil;
import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.ShopDO;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/3 16:16
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface ShopService {
    ShopDO createShop(ShopDO shopDO) throws BusinessException;

    ShopModel getById(Integer id);

    List<ShopModel> listAll(PageQueryUtil queryUtil);
}
