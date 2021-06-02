package com.hongjun.adminweb.service.impl;

import com.github.pagehelper.PageHelper;
import com.hongjun.adminweb.service.CategoryService;
import com.hongjun.adminweb.service.util.PageQueryUtil;
import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.CategoryDOExample;
import com.hongjun.mapper.CategoryDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/2 19:29
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDOMapper categoryDOMapper;

    @Override
    public void createCategory(CategoryDO categoryDO) {
        categoryDO.setCreatedAt(new Date());
        categoryDO.setUpdatedAt(new Date());
        categoryDOMapper.insertSelective(categoryDO);
    }

    @Override
    public List<CategoryDO> listPage(PageQueryUtil pageQueryUtil) {
        PageHelper.startPage(pageQueryUtil.getPageNum(), pageQueryUtil.getPageSize());
        List<CategoryDO> list = categoryDOMapper.selectByExample(new CategoryDOExample());
        return list;
    }
}
