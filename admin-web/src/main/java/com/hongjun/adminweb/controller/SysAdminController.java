package com.hongjun.adminweb.controller;

import cn.hutool.core.collection.CollUtil;
import com.hongjun.adminweb.cache.SysAdminCache;
import com.hongjun.adminweb.service.SysAdminService;
import com.hongjun.adminweb.service.SysMenuService;
import com.hongjun.adminweb.service.SysRoleService;
import com.hongjun.adminweb.service.model.SysAdminLoginModel;
import com.hongjun.adminweb.service.model.SysAdminModel;
import com.hongjun.adminweb.valator.ValidatorUtil;
import com.hongjun.adminweb.vo.SysMenuVO;
import com.hongjun.common.controller.base.BaseController;
import com.hongjun.common.enums.EnumBusinessError;
import com.hongjun.common.error.BusinessException;
import com.hongjun.common.response.CommonReturnPage;
import com.hongjun.common.response.CommonReturnType;
import com.hongjun.common.validator.ValidationResult;
import com.hongjun.dataobject.SysAdminDO;
import com.hongjun.dataobject.SysMenuDO;
import com.hongjun.dataobject.SysRoleDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hongjun500
 * @date 2021/3/22 17:57
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Controller
@RequestMapping("/admin")
@Api(value = "后台用户控制器")
public class SysAdminController extends BaseController{

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private SysAdminCache sysAdminCache;

    @Autowired
    private SysAdminService sysAdminService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleService sysRoleService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }


    @GetMapping(value = "/listView")
    public String listView(){
        return "views/sys/admin/list";
    }

    @GetMapping(value = "/addView")
    public String addView(){
        return "views/sys/admin/add";
    }


    @GetMapping(value = "/editView/{id}")
    public String editView(Model model, @PathVariable Long id){
        SysAdminDO sysAdminDO = sysAdminService.getSysAdminDOById(id);
        model.addAttribute("sysAdminDO", sysAdminDO);
        return "views/sys/admin/edit";
    }

    @GetMapping(value = "/allotRoleView")
    public String allotRoleView(){
        return "views/sys/admin/allotRole";
    }

    /**
     * 初始化数据
     * @param principal
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "/init")
    @ResponseBody
    public Map<Object, Object> init(Principal principal) throws BusinessException {
        Map<Object, Object> map = new HashMap<>(16);
        Map<Object, Object> home = new HashMap<>(16);
        Map<Object, Object> logo = new HashMap<>(16);
        if(principal==null){
            throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN);
        }
        String username = principal.getName();
        SysAdminDO sysAdminDO = sysAdminCache.getSysAdminDO(username);
        List<SysMenuVO> sysMenuVOList = sysMenuService.treeListAdminId(sysAdminDO.getId());

        map.put("menuInfo", sysMenuVOList);
        home.put("title", "首页");
        //控制器路由,自行定义
        home.put("href","/layuimini/page/icon.html");
        logo.put("title","点评搜索头条");
        logo.put("href","/admin/index");
        //静态资源文件路径,可使用默认的logo.png
        logo.put("image","/layuimini/images/logo.png");
        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        return map;
    }


    @ApiOperation("根据用户名或昵称分页获取用户列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnPage list(@RequestParam(value = "name", required = false) String keyword,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<SysAdminDO> list = sysAdminService.list(keyword, pageSize, pageNum);
        return CommonReturnPage.createPage(list);
    }


    @GetMapping(value = "/index")
    public String index(Model model){
        /*List<SysMenuNodeModel> sysMenuNodeModels = sysMenuService.treeList();
        model.addAttribute("menus", sysMenuNodeModels);*/
        return "/index";
    }

    @ApiOperation(value = "登录获取token")
    @PostMapping(value = "/login")
    @ResponseBody
    public CommonReturnType<Map<String, String>> login(@RequestBody SysAdminLoginModel sysAdminLoginModel, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        ValidationResult validate = validatorUtil.validate(sysAdminLoginModel);
        if (validate.getHasErrors()) {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, validate.getErrorMsg());
        }
        String token = sysAdminService.login(sysAdminLoginModel.getUsername(), sysAdminLoginModel.getPassword(), request, response);
        if (token == null) {
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        }
        Map<String, String> tokenMap = new HashMap<>(16);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonReturnType.create(tokenMap);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    @ResponseBody
    public CommonReturnType<SysAdminDO> register(@Validated @RequestBody SysAdminModel sysAdminModel) throws BusinessException {
        ValidationResult validate = validatorUtil.validate(sysAdminModel);
        if (validate.getHasErrors()) {
            throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR, validate.getErrorMsg());
        }
        SysAdminDO data = sysAdminService.register(sysAdminModel);
        return CommonReturnType.create(data);
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    @ResponseBody
    public CommonReturnType<Map<String, String>> refreshToken(HttpServletRequest request) throws BusinessException {
        String token = request.getHeader(tokenHeader);
        String refreshToken = sysAdminService.refreshToken(token);
        // token过期
        if (refreshToken == null) {
            throw new BusinessException(EnumBusinessError.TOKEN_PAST);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonReturnType.create(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    @ResponseBody
    public CommonReturnType<Map<String, Object>> getAdminInfo(Principal principal) throws BusinessException {
        if(principal==null){
            throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN);
        }
        String username = principal.getName();
        SysAdminDO sysAdminDO = sysAdminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", sysAdminDO.getUsername());
        data.put("menus", sysRoleService.listByAdminId(sysAdminDO.getId()));
        data.put("icon", sysAdminDO.getIcon());
        List<SysRoleDO> roleList = sysAdminService.listRoleByAdminId(sysAdminDO.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(SysRoleDO::getName).collect(Collectors.toList());
            Map<String, Long> map = roleList.stream().collect(Collectors.toMap(SysRoleDO::getName, SysRoleDO::getId));
            data.put("roles",roles);
            data.put("map", map);
        }
        return CommonReturnType.create(data);
    }

    @ApiOperation(value = "登出")
    @PostMapping(value = "/logout")
    @ResponseBody
    public CommonReturnType<Object> logout(Principal principal, HttpServletResponse response) throws BusinessException {
        // 清空Cookie
        Cookie cookie = new Cookie(tokenHeader, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        // 删除缓存
        if(principal==null){
            throw new BusinessException(EnumBusinessError.USER_NOT_LOGIN);
        }
        String username = principal.getName();
        SysAdminDO sysAdminDO = sysAdminCache.getSysAdminDO(username);
        sysAdminCache.delAdmin(sysAdminDO.getId());
        return CommonReturnType.create(null);
    }



    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    @ResponseBody
    public CommonReturnType updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) throws BusinessException {
        int count = sysAdminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role/{adminId}")
    @ResponseBody
    public CommonReturnType<List<SysRoleDO>> getRoleList(@PathVariable Long adminId) {
        List<SysRoleDO> roleList = sysAdminService.listRoleByAdminId(adminId);
        return CommonReturnType.create(roleList);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public CommonReturnType update(@PathVariable Long id, @RequestBody SysAdminDO admin) throws BusinessException {
        int count = sysAdminService.update(id, admin);
        if (count > 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType delete(@PathVariable Long id) throws BusinessException {
        int count = sysAdminService.delete(id);
        if (count > 0) {
            return CommonReturnType.create(count);
        }
        throw new BusinessException(EnumBusinessError.UNKNOWN_ERROR);
    }
}
