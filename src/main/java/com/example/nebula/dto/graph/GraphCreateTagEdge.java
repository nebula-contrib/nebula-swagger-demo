package com.example.nebula.dto.graph;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("创建tag edge入参")
public class GraphCreateTagEdge {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称",example = "flceshi")
    private String space;

    @ApiModelProperty(value = "创建类型: tag/edge",example = "tag")
    private String type;

    @ApiModelProperty(value = "tag/edge名称",example = "demo")
    private String tagEdgeName;

    @ApiModelProperty(value = "tag/edge描述",example = "备注")
    private String tagEdgeComment;

    @ApiModelProperty(value = "属性集合")
    private List<PropertyBean> propertyList;
}
