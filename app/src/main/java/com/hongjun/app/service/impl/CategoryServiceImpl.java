package com.hongjun.app.service.impl;

import com.hongjun.app.service.CategoryService;
import com.hongjun.dataobject.CategoryDO;
import com.hongjun.dataobject.CategoryDOExample;
import com.hongjun.mapper.CategoryDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/2 22:17
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDOMapper categoryDOMapper;

    @Override
    public List<CategoryDO> listAll() {
        return categoryDOMapper.selectByExample(new CategoryDOExample());
    }
}
