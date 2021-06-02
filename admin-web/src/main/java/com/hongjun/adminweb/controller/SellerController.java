package com.hongjun.adminweb.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.hongjun.adminweb.service.SellerService;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidationResult;
import com.hongjun.common.validator.ValidatorImpl;
import com.hongjun.dataobject.SellerDO;
import com.hongjun.dataobject.SysMenuDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/1 23:48
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@RequestMapping(value = "/seller")
@Controller
public class SellerController extends BaseController {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private SellerService sellerService;


    @GetMapping(value = "/addView")
    public String addView(){
        return "views/seller/add";
    }

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/seller/list";
    }


    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType create(@RequestBody SellerDO sellerDO) throws BusinessException {
        ValidationResult validate = validator.validate(sellerDO);
        if (validate.getHasErrors()){
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, validate.getErrorMsg());
        }
        sellerService.createSeller(sellerDO);
        return CommonReturnType.create(null);
    }


    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnPage<SellerDO> list(
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "level", required = false) Integer level) {
        List<SellerDO> list = sellerService.listAll(pageNum, pageSize);
        return CommonReturnPage.createPage(list);
    }


    @ApiOperation("启用/禁用")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonReturnType<SellerDO> changeStatus(@PathVariable Integer id, @RequestParam(value = "status") Integer status) throws BusinessException {
        SellerDO sellerDO = sellerService.changeStatus(id, status);
        return CommonReturnType.create(sellerDO);
    }
}
