package com.example.nebula.dto.graph;

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
@ApiModel("删除属性实体")
public class GraphDelAttribute {


    @ApiModelProperty(value = "空间名称", example = "flceshi",required = true)
    private String space;

    @ApiModelProperty(value = "attribute可选值: tag标签/edge边类型",example = "tag",required = true)
    private String attribute;

    @ApiModelProperty(value = "属性名称", example = "t1",required = true)
    private String attributeName;

    @ApiModelProperty(value = "tag/edge的属性名称", example = "p5")
    private String propertyName;
}
