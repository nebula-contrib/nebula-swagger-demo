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
@ApiModel("查询索引入参")
public class GraphShowIndex {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称",example = "flceshi",required = true)
    private String space;
    /**
     * attribute:  tag/edge
     **/
    @ApiModelProperty(value = "属性可选: tag/edge",example = "tag",required = true)
    private String attribute;

}
