package com.hongjun.adminweb.controller;

import com.hongjun.adminweb.service.SysResourceService;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.config.DynamicSecurityMetadataSource;
import com.hongjun.dataobject.SysResourceCategoryDO;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysRoleDO;
import com.hongjun.mapper.SysResourceDOMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 19:33
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Controller
@RequestMapping(value = "/resource")
public class SysResourceController extends BaseController {

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/sys/resources/list";
    }

    @GetMapping(value = "/addView")
    public String addView(){
        return "views/sys/resources/add";
    }

    @GetMapping(value = "/editView/{id}")
    public String editView(@PathVariable Long id, Model model){
        SysResourceDO sysResourceDO = sysResourceService.getItem(id);
        model.addAttribute("sysResourceDO", sysResourceDO);
        return "views/sys/resources/edit";
    }

    @ApiOperation("添加后台资源")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType create(@RequestBody SysResourceDO sysResourceDO) throws BusinessException {
        int count = sysResourceService.create(sysResourceDO);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("修改后台资源")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonReturnType update(@PathVariable Long id,
                               @RequestBody SysResourceDO sysResourceDO) throws BusinessException {
        int count = sysResourceService.update(id, sysResourceDO);
        if (count > 0) {
            dynamicSecurityMetadataSource.clearDataSource();
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public CommonReturnType<SysResourceDO> getItem(@PathVariable Long id) {
        SysResourceDO sysResourceDO = sysResourceService.getItem(id);
        return CommonReturnType.create(sysResourceDO);
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    public CommonReturnType delete(@PathVariable Long id) throws BusinessException {
        int count = sysResourceService.delete(id);
        if (count > 0) {
            dynamicSecurityMetadataSource.clearDataSource();
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnPage<SysResourceDO> list(@RequestParam(required = false) Long categoryId,
                                 @RequestParam(required = false, value = "name") String nameKeyword,
                                 @RequestParam(required = false, value = "url") String urlKeyword,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SysResourceDO> resourceList = sysResourceService.list(categoryId,nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonReturnPage.createPage(resourceList);
    }

    @ApiOperation("查询所有后台资源")
    @GetMapping(value = "/listAll")
    @ResponseBody
    public CommonReturnType<List<SysResourceDO>> listAll() {
        List<SysResourceDO> resourceList = sysResourceService.listAll();
        return CommonReturnType.create(resourceList);
    }
}
