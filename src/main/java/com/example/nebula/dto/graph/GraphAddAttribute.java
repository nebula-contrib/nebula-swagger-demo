package com.example.nebula.dto.graph;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("修改属性实体")
public class GraphAddAttribute {


    @ApiModelProperty(value = "空间名称", example = "flceshi",required = true)
    private String space;

    @ApiModelProperty(value = "attribute可选值: tag标签/edge边类型",example = "tag",required = true)
    private String attribute;

    @ApiModelProperty(value = "属性名称", example = "t1",required = true)
    private String attributeName;

    @ApiModelProperty(value = "tag/edge的属性名称", example = "p5")
    private String propertyName;

    @ApiModelProperty(value = "属性类型,add 必传该类型 可选值: int bool string double .........", example = "string",required = false)
    private String propertyType;

    @ApiModelProperty(value = "是否可为空: NOT NULL 或者 NULL", example = "NULL")
    private String isNull;

    @ApiModelProperty(value = "默认值")
    private Object defaultValue;

    @ApiModelProperty(value = "描述")
    private String common;

    public Object getDefaultValue() {
        if (!ObjectUtil.isNull(defaultValue)) {
            if (defaultValue instanceof String) {
                return "DEFAULT '" + defaultValue + "'";
            }
            return "DEFAULT " + defaultValue;
        }
        return defaultValue;
    }

    public String getCommon() {
        if (StrUtil.isNotBlank(common)) {
            return  "COMMENT '" + common + "'";
        }
        return common;
    }
}
