package com.hongjun.adminweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.service.SysMenuService;
import com.hongjun.adminweb.service.model.SysMenuNodeModel;
import com.hongjun.adminweb.vo.SysMenuVO;
import com.hongjun.dataobject.SysMenuDO;
import com.hongjun.dataobject.SysMenuDOExample;
import com.hongjun.mapper.SysMenuDOMapper;
import com.hongjun.mapper.SysRoleDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hongjun500
 * @date 2021/3/23 21:02
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDOMapper sysMenuDOMapper;

    @Autowired
    private SysRoleDOMapper sysRoleDOMapper;

    @Override
    public int create(SysMenuDO sysMenuDO) {
        sysMenuDO.setCreateTime(new Date());
        hasParent(sysMenuDO);
        // updateLevel(sysMenuDO);
        return sysMenuDOMapper.insertSelective(sysMenuDO);
    }

    /**
     * 根据菜单级别判断有无父级菜单处理
     * @param sysMenuDO
     */
    private void hasParent(SysMenuDO sysMenuDO){
        // 顶级菜单无父级菜单
        if (sysMenuDO.getLevel() == 0){
            sysMenuDO.setParentId(0L);
        }
        // 一级菜单默认链接空字符串
        if (sysMenuDO.getLevel() == 1) {
            sysMenuDO.setName("");
        }
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(SysMenuDO sysMenuDO) {

        if (sysMenuDO.getParentId() == 0) {
            //没有父菜单时为一级菜单
            sysMenuDO.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            SysMenuDO parentMenu = sysMenuDOMapper.selectByPrimaryKey(sysMenuDO.getParentId());
            if (parentMenu != null) {
                sysMenuDO.setLevel(parentMenu.getLevel() + 1);
            } else {
                sysMenuDO.setLevel(0);
            }
        }
    }

    @Override
    public int update(Long id, SysMenuDO sysMenuDO) {
        sysMenuDO.setId(id);
        hasParent(sysMenuDO);
        // updateLevel(sysMenuDO);
        return sysMenuDOMapper.updateByPrimaryKeySelective(sysMenuDO);
    }

    @Override
    public SysMenuDO getSysMenuDOById(Long id) {
        return sysMenuDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return sysMenuDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysMenuDO> list(Long parentId, Integer pageSize, Integer pageNum, String title, Integer level) {
        SysMenuDOExample example = new SysMenuDOExample();
        SysMenuDOExample.Criteria criteria = example.createCriteria();
        if (parentId != null ){
            criteria.andParentIdEqualTo(parentId);
        }
        if (level != null){
            criteria.andLevelEqualTo(level);
        }
        if (StrUtil.isNotBlank(title)){
            criteria.andTitleLike('%' + title + '%');
        }
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        PageHelper.startPage(pageNum, pageSize);
        return sysMenuDOMapper.selectByExample(example);
    }

    @Override
    public List<SysMenuVO> treeListAdminId(Long adminId) {
        List<SysMenuDO> sysMenuDOList = sysMenuDOMapper.listByAdminId(adminId);
        List<SysMenuVO> sysMenuVOList = new ArrayList<>();
        for (SysMenuDO sysMenuDO:sysMenuDOList){
            SysMenuVO sysMenuVO = new SysMenuVO();
            sysMenuVO.setId(sysMenuDO.getId());
            sysMenuVO.setPid(sysMenuDO.getParentId());
            sysMenuVO.setTitle(sysMenuDO.getTitle());
            sysMenuVO.setHref(sysMenuDO.getName());
            sysMenuVO.setIcon(sysMenuDO.getIcon());
            sysMenuVO.setSort(sysMenuDO.getSort());
            sysMenuVO.setLevel(sysMenuDO.getLevel());
            sysMenuVO.setStatus(sysMenuDO.getHidden());
            sysMenuVOList.add(sysMenuVO);
        }
        // 转换树形结果
        return sysMenuVOList.stream()
                // 匹配父级
                .filter(sysMenuVO -> sysMenuVO.getPid().equals(0L))
                .map(sysMenuVO -> covertMenuVO(sysMenuVO, sysMenuVOList)).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuNodeModel> treeList() {
        List<SysMenuDO> menuList = sysMenuDOMapper.selectByExample(new SysMenuDOExample());
        return menuList.stream()
                // 匹配父级
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDO> listMenuBySuperLevel(Integer level){
        SysMenuDOExample sysMenuDOExample = new SysMenuDOExample();
        sysMenuDOExample.createCriteria().andLevelEqualTo(level);
        return sysMenuDOMapper.selectByExample(sysMenuDOExample);
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        SysMenuDO sysMenuDO = new SysMenuDO();
        sysMenuDO.setHidden(hidden);
        sysMenuDO.setId(id);
        return sysMenuDOMapper.updateByPrimaryKeySelective(sysMenuDO);
    }

    /**
     * 将SysMenuDO转化为SysMenuNodeModel并设置children属性
     */
    private SysMenuNodeModel covertMenuNode(SysMenuDO menu, List<SysMenuDO> menuList) {
        SysMenuNodeModel node = new SysMenuNodeModel();
        BeanUtils.copyProperties(menu, node);
        List<SysMenuNodeModel> children = menuList.stream()
                // 确认父子关系
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChild(children);
        return node;
    }


    /**
     * 将SysMenuVO转化为符合前端UI框架需要的树形结构并设置children属性
     */
    private SysMenuVO covertMenuVO(SysMenuVO sysMenuVO, List<SysMenuVO> menuList) {
        SysMenuVO node = new SysMenuVO();
        BeanUtils.copyProperties(sysMenuVO, node);
        List<SysMenuVO> children = menuList.stream()
                // 确认父子关系
                .filter(subMenu -> subMenu.getPid().equals(sysMenuVO.getId()))
                .map(subMenu -> covertMenuVO(subMenu, menuList)).collect(Collectors.toList());
        node.setChild(children);
        return node;
/*
        SysMenuNodeModel node = new SysMenuNodeModel();
        BeanUtils.copyProperties(menu, node);
        List<SysMenuNodeModel> children = menuList.stream()
                // 确认父子关系
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChild(children);*/
    }
}
