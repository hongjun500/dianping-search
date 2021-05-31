package com.hongjun.adminweb.controller;

import com.hongjun.adminweb.service.SysRoleService;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.dataobject.SysMenuDO;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysRoleDO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 14:18
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 后台角色控制器
 */
@Controller
@RequestMapping(value = "/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;


    @GetMapping(value = "/allotMenuView")
    public String allotMenuView(){
        return "views/sys/role/allotMenu";
    }

    @GetMapping(value = "/listView")
    public String listView(){
        return "views/sys/role/list";
    }

    @GetMapping(value = "/addView")
    public String addView(){
        return "views/sys/role/add";
    }

    @GetMapping(value = "/editView/{id}")
    public String editView(@PathVariable Long id, Model model){
        SysRoleDO sysRoleDO = sysRoleService.getSysRoleById(id);
        model.addAttribute("sysRoleDO", sysRoleDO);
        return "views/sys/role/edit";
    }

    @ApiOperation("添加角色")
    @PostMapping(value = "/create")
    @ResponseBody
    public CommonReturnType<Object> create(@RequestBody SysRoleDO sysRoleDO) throws BusinessException {
        int count = sysRoleService.create(sysRoleDO);
        if (count > 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonReturnType<Object> update(@PathVariable Long id, @RequestBody SysRoleDO role) throws BusinessException {
        int count = sysRoleService.update(id, role);
        if (count > 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delete")
    @ResponseBody
    public CommonReturnType<Object> delete(@RequestParam("ids") List<Long> ids) throws BusinessException {
        int count = sysRoleService.delete(ids);
        if (count > 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    @ResponseBody
    public List<SysRoleDO> listAll() {
        List<SysRoleDO> roleList = sysRoleService.list();
        // return CommonReturnType.create(roleList);
        return roleList;
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnPage list(@RequestParam(value = "name", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SysRoleDO> roleList = sysRoleService.list(keyword, pageSize, pageNum);
        return CommonReturnPage.createPage(roleList);
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    @ResponseBody
    public CommonReturnType updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) throws BusinessException {
        SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setStatus(status);
        int count = sysRoleService.update(id, sysRoleDO);
        if (count > 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu/{roleId}")
    @ResponseBody
    public CommonReturnType<List<SysMenuDO>> listMenu(@PathVariable Long roleId) {
        List<SysMenuDO> roleList = sysRoleService.listMenuByRoleId(roleId);
        return CommonReturnType.create(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    @ResponseBody
    public CommonReturnType<List<SysResourceDO>> listResource(@PathVariable Long roleId) {
        List<SysResourceDO> roleList = sysRoleService.listResourceByRoleId(roleId);
        return CommonReturnType.create(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    @ResponseBody
    public CommonReturnType allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = sysRoleService.allocMenu(roleId, menuIds);
        return CommonReturnType.create(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    @ResponseBody
    public CommonReturnType allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = sysRoleService.allocResource(roleId, resourceIds);
        return CommonReturnType.create(count);
    }
}
