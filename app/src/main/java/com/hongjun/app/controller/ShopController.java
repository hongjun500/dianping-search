package com.hongjun.app.controller;

import com.hongjun.app.service.ShopService;
import com.hongjun.app.service.model.ShopModel;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidationResult;
import com.hongjun.common.validator.ValidatorImpl;
import com.hongjun.dataobject.ShopDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/1 23:48
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@RequestMapping(value = "/shop")
@Controller
public class ShopController extends BaseController {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ShopService shopService;


    @ApiOperation("获取门店")
    @GetMapping(value = "/recommend")
    @ResponseBody
    public CommonReturnType<List<ShopModel>> recommend(@RequestParam(value = "longitude") BigDecimal longitude,
                                            @RequestParam(value = "latitude") BigDecimal latitude) throws BusinessException {
        if (latitude == null || longitude == null){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<ShopModel> shopModelList = shopService.recommend(longitude, latitude);
        return CommonReturnType.create(shopModelList);
    }
}
