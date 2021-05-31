package com.hongjun.adminweb.service;

import com.hongjun.dataobject.SysResourceCategoryDO;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 20:30
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface SysResourceCategoryService {

    SysResourceCategoryDO getSysResourceCategoryDOById(Long id);

    /**
     * 获取所有资源分类
     */
    List<SysResourceCategoryDO> listAll();

    /**
     * 分页获取资源分类列表
     * @param pageSize
     * @param pageNum
     * @param name
     * @return
     */
    List<SysResourceCategoryDO> listPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 创建资源分类
     */
    int create(SysResourceCategoryDO sysResourceCategoryDO);

    /**
     * 修改资源分类
     */
    int update(Long id, SysResourceCategoryDO sysResourceCategoryDO);

    /**
     * 删除资源分类
     */
    int delete(Long id);
}
