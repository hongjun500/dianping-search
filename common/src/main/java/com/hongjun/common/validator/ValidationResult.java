package com.hongjun.common.validator;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongjun500
 * @date 2020/6/21 20:39
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 数据校验结果
 */
public class ValidationResult {
    /**
     * 校验是否有错
     */
    private Boolean hasErrors = false;

    /**
     * 错误信息
     */
    private Map<String, String> errorMsgMap = new HashMap<>();

    public Boolean getHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * 通用的通过格式化字符串信息获取错误结果的msg方法
     * @return errorMsg
     */
    public String getErrorMsg(){
        return StrUtil.join(errorMsgMap.values().toString(),",");
    }
}
