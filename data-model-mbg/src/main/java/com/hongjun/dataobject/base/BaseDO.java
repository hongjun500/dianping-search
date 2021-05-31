package com.hongjun.dataobject.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hongjun500
 * @date 2021/3/17 16:19
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description: 数据基类
 */
@Data
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 694532704127007952L;

    @TableField("create_by")
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;

    @TableField("update_by")
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updateBy;

    @TableField("create_time")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @TableField("update_time")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;
}
