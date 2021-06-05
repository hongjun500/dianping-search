package com.hongjun.adminweb.controller;

import com.hongjun.adminweb.service.CategoryService;
import com.hongjun.adminweb.service.ShopService;
import com.hongjun.adminweb.service.model.ShopModel;
import com.hongjun.adminweb.service.util.PageQueryUtil;
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
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "/addView")
    public String addView(){
        return "views/shop/add";
    }

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/shop/list";
    }


    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType create(@RequestBody ShopDO shopDO) throws BusinessException {
        ValidationResult validate = validator.validate(shopDO);
        if (validate.getHasErrors()){
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, validate.getErrorMsg());
        }
        shopService.createShop(shopDO);
        return CommonReturnType.create(shopDO);
    }


    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnPage<ShopModel> list(
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<ShopModel> shopModelList = shopService.listAll(PageQueryUtil.create(pageNum, pageSize));
        return CommonReturnPage.createPage(shopModelList);
    }
}
