package com.hongjun.adminweb.service.model;

import com.hongjun.dataobject.SysMenuDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author hongjun500
 * @date 2021/3/23 21:00
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuNodeModel extends SysMenuDO {
    private static final long serialVersionUID = -1566220192639737596L;
    @ApiModelProperty(value = "子级菜单")
    private List<SysMenuNodeModel> child;
}
