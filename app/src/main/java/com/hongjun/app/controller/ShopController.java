package com.hongjun.app.controller;

import cn.hutool.core.util.StrUtil;
import com.hongjun.app.service.CategoryService;
import com.hongjun.app.service.ShopService;
import com.hongjun.app.service.model.ShopModel;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidationResult;
import com.hongjun.common.validator.ValidatorImpl;
import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.ShopDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CategoryService categoryService;


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


    @ApiOperation("搜索门店")
    @GetMapping(value = "/search")
    @ResponseBody
    public CommonReturnType<Map<Object, Object>> search(@RequestParam(value = "longitude") BigDecimal longitude,
                                                        @RequestParam(value = "latitude") BigDecimal latitude,
                                                        @RequestParam(value = "keyword") String keyword,
                                                        @RequestParam(value = "orderBy", required = false) Integer orderBy,
                                                        @RequestParam(value = "tags", required = false) String tags,
                                                        @RequestParam(value = "categoryId", required = false) Integer categoryId) throws BusinessException {
        if (StrUtil.isBlank(keyword) || latitude == null || longitude == null){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        List<ShopModel> shopModelList = shopService.search(longitude, latitude, keyword, orderBy, categoryId, tags);
        Map<Object, Object> map = new HashMap<>(16);
        List<CategoryDO> categoryDOList = categoryService.listAll();
        List<Map<String, Object>> tagsAggregation = shopService.searchByGroupTags(keyword, categoryId, tags);
        map.put("shop", shopModelList);
        map.put("category", categoryDOList);
        map.put("tags", tagsAggregation);
        return CommonReturnType.create(map);
    }
}
