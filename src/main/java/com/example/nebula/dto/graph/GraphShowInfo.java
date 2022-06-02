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
@ApiModel("属性详情查询入参")
public class GraphShowInfo {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称",example = "flceshi")
    private String space;

    /**
     * attribute:  tag /edge
     **/
    @ApiModelProperty(value = "属性类型:tag/edge",example = "tag")
    private String attribute;

    /**
     * attributeName:  tag 名称/edge 名称
     **/
    @ApiModelProperty(value = "属性名称")
    private String attributeName;

}
