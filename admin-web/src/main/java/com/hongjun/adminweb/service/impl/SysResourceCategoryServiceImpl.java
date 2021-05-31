package com.hongjun.adminweb.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.service.SysResourceCategoryService;
import com.hongjun.dataobject.SysResourceCategoryDO;
import com.hongjun.dataobject.SysResourceCategoryDOExample;
import com.hongjun.mapper.SysResourceCategoryDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 20:30
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class SysResourceCategoryServiceImpl implements SysResourceCategoryService {

    @Autowired
    private SysResourceCategoryDOMapper sysResourceCategoryDOMapper;


    @Override
    public SysResourceCategoryDO getSysResourceCategoryDOById(Long id) {
        SysResourceCategoryDO sysResourceCategoryDO = sysResourceCategoryDOMapper.selectByPrimaryKey(id);
        return sysResourceCategoryDO;
    }

    @Override
    public List<SysResourceCategoryDO> listAll() {
        SysResourceCategoryDOExample example = new SysResourceCategoryDOExample();
        example.setOrderByClause("sort desc");
        return sysResourceCategoryDOMapper.selectByExample(example);
    }

    @Override
    public List<SysResourceCategoryDO> listPage(Integer pageNum, Integer pageSize, String name) {
        SysResourceCategoryDOExample example = new SysResourceCategoryDOExample();
        SysResourceCategoryDOExample.Criteria criteria = example.createCriteria();
        if (StrUtil.isNotBlank(name)) {
            criteria.andNameLike('%' + name + '%');
        }
        PageHelper.startPage(pageNum, pageSize);
        return sysResourceCategoryDOMapper.selectByExample(example);
    }

    @Override
    public int create(SysResourceCategoryDO sysResourceCategoryDO) {
        sysResourceCategoryDO.setCreateTime(new Date());
        return sysResourceCategoryDOMapper.insertSelective(sysResourceCategoryDO);
    }

    @Override
    public int update(Long id, SysResourceCategoryDO sysResourceCategoryDO) {
        sysResourceCategoryDO.setId(id);
        return sysResourceCategoryDOMapper.updateByPrimaryKeySelective(sysResourceCategoryDO);
    }

    @Override
    public int delete(Long id) {
        return sysResourceCategoryDOMapper.deleteByPrimaryKey(id);
    }
}
