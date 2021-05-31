package com.hongjun.adminweb.service;

import com.hongjun.dataobject.SysResourceDO;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/22 15:53
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public interface SysResourceService {
    /**
     * 查询所有资源
     * @return
     */
    List<SysResourceDO> listAll();

    /**
     * 添加资源
     */
    int create(SysResourceDO sysResourceDO);

    /**
     * 修改资源
     */
    int update(Long id, SysResourceDO sysResourceDO);

    /**
     * 获取资源详情
     */
    SysResourceDO getItem(Long id);

    /**
     * 删除资源
     */
    int delete(Long id);

    /**
     * 分页查询资源
     */
    List<SysResourceDO> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
