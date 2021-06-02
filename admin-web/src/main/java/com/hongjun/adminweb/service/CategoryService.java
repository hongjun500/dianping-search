package com.hongjun.adminweb.service;

import com.hongjun.adminweb.service.util.PageQueryUtil;
import com.hongjun.dataobject.CategoryDO;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/6/2 19:29
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface CategoryService {
    void createCategory(CategoryDO categoryDO);
    List<CategoryDO> listPage(PageQueryUtil pageQueryUtil);
}
