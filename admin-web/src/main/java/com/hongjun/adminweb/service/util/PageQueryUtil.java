package com.hongjun.adminweb.service.util;

import com.github.pagehelper.PageHelper;
import lombok.Data;

/**
 * @author hongjun500
 * @date 2021/6/2 19:31
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@Data
public class PageQueryUtil {
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public static PageQueryUtil create(Integer pageNum, Integer pageSize){
        PageQueryUtil pageQueryUtil = new PageQueryUtil();
        pageQueryUtil.setPageNum(pageNum);
        pageQueryUtil.setPageSize(pageSize);
        return pageQueryUtil;
    }
}
