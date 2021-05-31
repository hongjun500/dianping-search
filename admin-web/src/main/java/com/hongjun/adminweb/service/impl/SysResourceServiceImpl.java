package com.hongjun.adminweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.service.SysResourceService;
import com.hongjun.dataobject.SysResourceDO;
import com.hongjun.dataobject.SysResourceDOExample;
import com.hongjun.mapper.SysResourceDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/22 15:56
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private SysResourceDOMapper sysResourceDOMapper;

    @Override
    public List<SysResourceDO> listAll() {
        return sysResourceDOMapper.selectSysResourceAll(new SysResourceDOExample());
    }

    @Override
    public int create(SysResourceDO sysResourceDO) {
        sysResourceDO.setCreateTime(new Date());
        return sysResourceDOMapper.insertSelective(sysResourceDO);
    }

    @Override
    public int update(Long id, SysResourceDO sysResourceDO) {
        sysResourceDO.setId(id);
        return sysResourceDOMapper.updateByPrimaryKeySelective(sysResourceDO);
    }

    @Override
    public SysResourceDO getItem(Long id) {
        return sysResourceDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return sysResourceDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysResourceDO> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        SysResourceDOExample example = new SysResourceDOExample();
        SysResourceDOExample.Criteria criteria = example.createCriteria();
        if (categoryId != null){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (StrUtil.isNotBlank(nameKeyword)){
            criteria.andNameLike('%' + nameKeyword + '%');
        }
        if (StrUtil.isNotBlank(urlKeyword)){
            criteria.andUrlLike('%' + urlKeyword + '%');
        }
        return sysResourceDOMapper.selectByExample(example);
    }
}
