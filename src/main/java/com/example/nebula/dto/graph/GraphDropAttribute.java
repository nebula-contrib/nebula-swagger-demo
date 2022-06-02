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
public class GraphDropAttribute {


    @ApiModelProperty(value = "空间名称", example = "flceshi1",required = true)
    private String space;

    @ApiModelProperty(value = "属性名称", example = "flceshi1",required = true)
    private String attributeName;

    @ApiModelProperty(value = "attribute可选值:space空间/tag标签/edge边类型",example = "space",required = true)
    private String attribute;
}
