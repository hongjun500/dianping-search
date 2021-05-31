package com.hongjun.common.response;

import cn.hutool.core.util.IdcardUtil;
import lombok.Data;

/**
 * @author hongjun500
 * @date 2021/3/17 14:12
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 通用返回对象---正确信息
 */
@Data
public class CommonReturnType<T> {
    /**
     * 对应返回处理结果(成功与否)
     * success
     * fail
     */
    private String status;
    /**
     * 若status=success,则对应data内返回前端需要的json数据
     * 若status=fail,则对应data内使用通用的错误码格式
     */
    private T data;

    /**
     * 定义一个通用的创建方法
     * @param object
     * @return
     */
    public static<T> CommonReturnType<T> create(T object){
        return CommonReturnType.create(object,"success");
    }

    public static<T> CommonReturnType<T> create(T object, String status){
        CommonReturnType<T> type = new CommonReturnType<>();
        type.setData(object);
        type.setStatus(status);
        return type;
    }
}
