package com.hongjun.adminweb.controller;

import com.hongjun.adminweb.service.SysMenuService;
import com.hongjun.adminweb.service.model.SysMenuNodeModel;
import com.hongjun.adminweb.vo.SysEleTreeMenuVO;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.dataobject.SysMenuDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 21:18
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@RequestMapping(value = "/menu")
@Controller
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/addView")
    public String addView(){
        return "views/sys/menu/add";
    }

    @GetMapping(value = "/editView/{id}")
    public String editView(@PathVariable Long id, Model model){
        SysMenuDO sysMenuDO = sysMenuService.getSysMenuDOById(id);
        model.addAttribute("sysMenuDO", sysMenuDO);
        return "views/sys/menu/edit";
    }

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/sys/menu/list";
    }

    @ApiOperation("查询上级菜单(默认查顶级)")
    @GetMapping(value = "/superLevelMenu")
    @ResponseBody
    public CommonReturnType superLevelMenu(@RequestParam(defaultValue = "1") Integer level) {
        // 查询上一级菜单 1 -> 0; 2 -> 1;
        int formLevel = level - 1;
        List<SysMenuDO> sysMenuDOS = sysMenuService.listMenuBySuperLevel(formLevel);
        return CommonReturnType.create(sysMenuDOS);
    }

    @ApiOperation("添加后台菜单")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType create(@RequestBody SysMenuDO sysMenuDO) throws BusinessException {
        int count = sysMenuService.create(sysMenuDO);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("修改后台菜单")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonReturnType update(@PathVariable Long id,
                                   @RequestBody SysMenuDO sysMenuDO) throws BusinessException {
        int count = sysMenuService.update(id, sysMenuDO);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("根据ID获取菜单详情")
    @PostMapping(value = "/{id}")
    @ResponseBody
    public CommonReturnType<SysMenuDO> getItem(@PathVariable Long id) {
        SysMenuDO sysMenuDO = sysMenuService.getSysMenuDOById(id);
        return CommonReturnType.create(sysMenuDO);
    }

    @ApiOperation("根据ID删除后台菜单")
    @PostMapping(value = "/delete/{id}")
    @ResponseBody
    public CommonReturnType delete(@PathVariable Long id) throws BusinessException {
        int count = sysMenuService.delete(id);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }

    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list/{parentId}")
    @ResponseBody
    public CommonReturnPage<SysMenuDO> list(@PathVariable Long parentId,
                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "level", required = false) Integer level) {
        if (parentId == null){
            parentId = 0L;
        }
        List<SysMenuDO> menuList = sysMenuService.list(parentId, pageSize, pageNum, title, level);
        return CommonReturnPage.createPage(menuList);
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping(value = "/treeList")
    @ResponseBody
    public CommonReturnType treeList() {
        List<SysMenuNodeModel> list = sysMenuService.treeList();

        List<SysEleTreeMenuVO> sysEleTreeMenuVOList = convertEleTreeVOfromSysMenuNode(list);
        return CommonReturnType.create(sysEleTreeMenuVOList);
    }

    private List<SysEleTreeMenuVO> convertEleTreeVOfromSysMenuNode(List<SysMenuNodeModel> list) {
        if (!list.isEmpty()) {
            List<SysEleTreeMenuVO> sysEleTreeMenuVOList = new ArrayList<>();
            for (SysMenuNodeModel sysMenuNodeModel : list) {
                SysEleTreeMenuVO sysEleTreeMenuVO = new SysEleTreeMenuVO();
                sysEleTreeMenuVO.setId(sysMenuNodeModel.getId());
                sysEleTreeMenuVO.setLabel(sysMenuNodeModel.getTitle());
                sysEleTreeMenuVO.setChildren(convertEleTreeVOfromSysMenuNode(sysMenuNodeModel.getChild()));
                sysEleTreeMenuVOList.add(sysEleTreeMenuVO);
            }
            return sysEleTreeMenuVOList;
        }
        return null;
    }

    @ApiOperation("修改菜单显示状态")
    @PostMapping(value = "/updateHidden/{id}")
    @ResponseBody
    public CommonReturnType<Object> updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) throws BusinessException {
        int count = sysMenuService.updateHidden(id, hidden);
        if (count > 0) {
            return CommonReturnType.create(count);
        } else {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
        }
    }
}
