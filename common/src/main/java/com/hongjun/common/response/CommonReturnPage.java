package com.hongjun.common.response;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/17 14:35
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 通用返回对象---分页信息
 */
@Data
public class CommonReturnPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> data;
    private String status;

    public static <T> CommonReturnPage<T> createPage(List<T> list) {
        return CommonReturnPage.createPage(list, "success");
    }

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonReturnPage<T> createPage(List<T> list, String status) {
        CommonReturnPage<T> result = new CommonReturnPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setData(pageInfo.getList());
        result.setStatus(status);
        return result;
    }
}
