package com.example.nebula.dto.graph;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Descriptin:
 * @ClassName: PropertyBean
 */
@ApiModel("属性实体")
@Data
public class PropertyBean {

    /**
     * 属性名称
     **/
    @ApiModelProperty(value = "tag/edge属性名称", example = "name",required = true)
    private String propertyName;
    /**
     * 属性类型 (int bool string double .........)
     **/
    @ApiModelProperty(value = "tag/edge属性类型可选:int bool string double", example = "string",required = true)
    private String propertyType;
    /**
     * 属性描述
     **/
    @ApiModelProperty(value = "属性描述", example = "名称")
    private String propertyComment;

    /**
     * 是否可为空 (NOT NULL 或者 NULL)
     **/
    @ApiModelProperty(value = "是否可为空: NOT NULL 或者 NULL", example = "NULL")
    private String isNull;

    /**
     * 默认值
     **/
    @ApiModelProperty(value = "默认值",example = "NULL")
    private Object defaultValue;

    public Object getDefaultValue() {
        if (!ObjectUtil.isNull(defaultValue)) {
            if (defaultValue instanceof String) {
                return "DEFAULT '" + defaultValue + "'";
            }
            return "DEFAULT " + defaultValue;
        }
        return defaultValue;
    }
}
