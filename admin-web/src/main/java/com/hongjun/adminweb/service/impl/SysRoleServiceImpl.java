package com.hongjun.adminweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.cache.SysAdminCache;
import com.hongjun.adminweb.service.SysRoleService;
import com.hongjun.dataobject.*;
import com.hongjun.mapper.SysMenuDOMapper;
import com.hongjun.mapper.SysRoleDOMapper;
import com.hongjun.mapper.SysRoleMenuRelationDOMapper;
import com.hongjun.mapper.SysRoleResourceRelationDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 11:29
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDOMapper sysRoleDOMapper;

    @Autowired
    private SysMenuDOMapper sysMenuDOMapper;

    @Autowired
    private SysRoleMenuRelationDOMapper sysRoleMenuRelationDOMapper;

    @Autowired
    private SysRoleResourceRelationDOMapper sysRoleResourceRelationDOMapper;

    @Autowired
    private SysAdminCache sysAdminCache;

    @Override
    public int create(SysRoleDO role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return sysRoleDOMapper.insertSelective(role);
    }

    @Override
    public int update(Long id, SysRoleDO role) {
        role.setId(id);
        return sysRoleDOMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(List<Long> ids) {
        SysRoleDOExample example = new SysRoleDOExample();
        example.createCriteria().andIdIn(ids);
        int count = sysRoleDOMapper.deleteByExample(example);
        sysAdminCache.delResourceListByRoleIds(ids);
        return count;
    }

    @Override
    public SysRoleDO getSysRoleById(Long id) {
        return sysRoleDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysRoleDO> list() {
        return sysRoleDOMapper.selectByExample(new SysRoleDOExample());
    }

    @Override
    public List<SysRoleDO> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        SysRoleDOExample example = new SysRoleDOExample();
        if (StrUtil.isNotBlank(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        return sysRoleDOMapper.selectByExample(example);
    }

    @Override
    public List<SysMenuDO> listByAdminId(Long adminId) {
        return sysMenuDOMapper.listByAdminId(adminId);
    }

    @Override
    public List<SysMenuDO> listMenuByRoleId(Long roleId) {
        return sysRoleDOMapper.selectListByRoleId(roleId);
    }

    @Override
    public List<SysResourceDO> listResourceByRoleId(Long roleId) {
        return sysRoleDOMapper.selectResourceListByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        SysRoleMenuRelationDOExample example = new SysRoleMenuRelationDOExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        sysRoleMenuRelationDOMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId : menuIds) {
            SysRoleMenuRelationDO relation = new SysRoleMenuRelationDO();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            sysRoleMenuRelationDOMapper.insertSelective(relation);
        }
        return menuIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        SysRoleResourceRelationDOExample example = new SysRoleResourceRelationDOExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        sysRoleResourceRelationDOMapper.deleteByExample(example);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            SysRoleResourceRelationDO relation = new SysRoleResourceRelationDO();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            sysRoleResourceRelationDOMapper.insertSelective(relation);
        }
        sysAdminCache.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
