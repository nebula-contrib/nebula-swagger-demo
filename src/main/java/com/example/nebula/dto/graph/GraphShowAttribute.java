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
@ApiModel("查询相关属性入参")
public class GraphShowAttribute {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称",example = "flceshi",required = true)
    private String space;
    /**
     * attributes:  spaces/tags/edges
     **/
    @ApiModelProperty(value = "attribute可选值:spaces空间/tags标签/edges边类型",example = "spaces",required = true)
    private String attribute;

}
