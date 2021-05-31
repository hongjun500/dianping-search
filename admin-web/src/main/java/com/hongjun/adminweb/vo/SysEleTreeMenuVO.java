package com.hongjun.adminweb.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/31 10:50
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 基于eleTree树形菜单组件所需返回数据结构的菜单视图层
 */
@Data
public class SysEleTreeMenuVO {
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String label;

    private List<SysEleTreeMenuVO> children;
}
