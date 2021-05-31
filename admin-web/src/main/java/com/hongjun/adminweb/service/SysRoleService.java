package com.hongjun.adminweb.service;

import com.hongjun.dataobject.SysMenuDO;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysRoleDO;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 11:28
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface SysRoleService {
    /**
     * 添加角色
     */
    int create(SysRoleDO role);

    /**
     * 修改角色信息
     * @param id
     * @param role
     * @return
     */
    int update(Long id, SysRoleDO role);

    /**
     * 批量删除角色
     */
    int delete(List<Long> ids);

    SysRoleDO getSysRoleById(Long id);

    /**
     * 获取所有角色列表
     */
    List<SysRoleDO> list();

    /**
     * 分页获取角色列表
     */
    List<SysRoleDO> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<SysMenuDO> listByAdminId(Long adminId);

    /**
     * 获取角色相关菜单
     */
    List<SysMenuDO> listMenuByRoleId(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<SysResourceDO> listResourceByRoleId(Long roleId);

    /**
     * 给角色分配菜单
     */
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    int allocResource(Long roleId, List<Long> resourceIds);
}
