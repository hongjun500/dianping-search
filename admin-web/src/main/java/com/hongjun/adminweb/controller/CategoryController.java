package com.hongjun.adminweb.controller;

import com.hongjun.adminweb.service.CategoryService;
import com.hongjun.adminweb.service.SellerService;
import com.hongjun.adminweb.service.util.PageQueryUtil;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidationResult;
import com.hongjun.common.validator.ValidatorImpl;
import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.SellerDO;
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
@RequestMapping(value = "/category")
@Controller
public class CategoryController extends BaseController {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private CategoryService categoryService;


    @GetMapping(value = "/addView")
    public String addView(){
        return "views/category/add";
    }

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/category/list";
    }


    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType create(@RequestBody CategoryDO categoryDO) throws BusinessException {
        ValidationResult validate = validator.validate(categoryDO);
        if (validate.getHasErrors()){
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, validate.getErrorMsg());
        }
        categoryService.createCategory(categoryDO);
        return CommonReturnType.create(null);
    }


    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnPage<CategoryDO> list(
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "level", required = false) Integer level) {

        List<CategoryDO> list = categoryService.listPage(PageQueryUtil.create(pageNum, pageSize));
        return CommonReturnPage.createPage(list);
    }
}
