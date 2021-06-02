package com.hongjun.app.controller;

import com.hongjun.app.service.CategoryService;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.dataobject.CategoryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/2 22:18
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnType list(){
        List<CategoryDO> list = categoryService.listAll();
        return CommonReturnType.create(list);
    }
}
