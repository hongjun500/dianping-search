package com.hongjun.adminweb.controller;

import com.hongjun.adminweb.service.SysResourceCategoryService;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.dataobject.SysResourceCategoryDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 20:29
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Controller
@RequestMapping(value = "/resourceCategory")
public class SysResourceCategoryController extends BaseController {

    @Autowired
    private SysResourceCategoryService sysResourceCategoryService;

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/sys/resources/category/list";
    }

    @GetMapping(value = "/addView")
    public String addView(){
        return "views/sys/resources/category/add";
    }

    @GetMapping(value = "/editView/{id}")
    public String editView(@PathVariable Long id, Model model){
        SysResourceCategoryDO sysResourceCategoryDO = sysResourceCategoryService.getSysResourceCategoryDOById(id);
        model.addAttribute("sysResourceCategoryDO", sysResourceCategoryDO);
        return "views/sys/resources/category/edit";
    }

    @ApiOperation("分页查询所有后台资源分类")
    @GetMapping(value = "/listPage")
    @ResponseBody
    public CommonReturnPage<SysResourceCategoryDO> listPage(@RequestParam(value = "name", required = false) String keyword,
                                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SysResourceCategoryDO> resourceCategoryDOList = sysResourceCategoryService.listPage(pageNum, pageSize, keyword);
        return CommonReturnPage.createPage(resourceCategoryDOList);
    }

    @ApiOperation("查询所有后台资源分类")
    @GetMapping(value = "/listAll")
    @ResponseBody
    public CommonReturnType<List<SysResourceCategoryDO>> listAll() {
        List<SysResourceCategoryDO> resourceList = sysResourceCategoryService.listAll();
        return CommonReturnType.create(resourceList);
    }

    @ApiOperation("添加后台资源分类")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType<Object> create(@RequestBody SysResourceCategoryDO sysResourceCategoryDO) throws BusinessException {
        int count = sysResourceCategoryService.create(sysResourceCategoryDO);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("修改后台资源分类")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonReturnType<Object> update(@PathVariable Long id,
                               @RequestBody SysResourceCategoryDO sysResourceCategoryDO) throws BusinessException {
        int count = sysResourceCategoryService.update(id, sysResourceCategoryDO);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    public CommonReturnType<Object> delete(@PathVariable Long id) throws BusinessException {
        int count = sysResourceCategoryService.delete(id);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }
}
