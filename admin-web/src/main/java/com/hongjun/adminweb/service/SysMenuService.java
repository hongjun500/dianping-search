package com.hongjun.adminweb.service;

import com.hongjun.adminweb.service.model.SysMenuNodeModel;
import com.hongjun.adminweb.vo.SysMenuVO;
import com.hongjun.dataobject.SysMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 21:02
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface SysMenuService {
    /**
     * 创建后台菜单
     */
    int create(SysMenuDO sysMenuDO);

    /**
     * 修改后台菜单
     */
    int update(Long id, SysMenuDO sysMenuDO);

    /**
     * 根据ID获取菜单详情
     */
    SysMenuDO getSysMenuDOById(Long id);

    /**
     * 根据ID删除菜单
     */
    int delete(Long id);

    /**
     * 分页查询后台菜单
     */
    List<SysMenuDO> list(Long parentId, Integer pageSize, Integer pageNum, String title, Integer level);

    /**
     * 树形结构根据管理员id查询返回所有菜单列表
     */
    List<SysMenuVO> treeListAdminId(@Param(value = "adminId")Long adminId);

    /**
     * 树形结构返回所有菜单列表
     */
    List<SysMenuNodeModel> treeList();

    /**
     * 根据级别获取上级菜单
     * 1 -> 0
     * 2 -> 1
     */
    List<SysMenuDO> listMenuBySuperLevel(Integer level);

    /**
     * 修改菜单显示状态
     */
    int updateHidden(Long id, Integer hidden);
}
