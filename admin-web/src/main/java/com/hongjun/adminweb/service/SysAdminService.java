package com.hongjun.adminweb.service;

import com.hongjun.adminweb.service.model.SysAdminModel;
import com.hongjun.adminweb.service.model.SysAdminPasswordModel;
import com.hongjun.common.error.BusinessException;
import com.hongjun.dataobject.SysAdminDO;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysRoleDO;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author hongjun500
 */
public interface SysAdminService {

    /**
     * 根据用户名获取后台管理员
     */
    SysAdminDO getAdminByUsername(String username);

    /**
     * 注册功能
     */
    SysAdminDO register(SysAdminModel sysAdminModel) throws BusinessException;

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws BusinessException;

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    SysAdminDO getSysAdminDOById(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<SysAdminDO> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    int update(Long id, SysAdminDO admin);

    /**
     * 删除指定用户
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     */
    List<SysRoleDO> listRoleByAdminId(Long adminId);

    /**
     * 获取指定用户的可访问资源
     */
    List<SysResourceDO> getResourceList(Long adminId);

    /**
     * 修改密码
     */
    int updatePassword(SysAdminPasswordModel updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username) throws BusinessException;
}
