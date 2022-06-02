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
@ApiModel("插入边入参")
public class GraphInsertEdge {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "边类型edge名称", example = "e1", required = true)
    private String edgeName;

    @ApiModelProperty(value = "边类型edge属性集合", required = false)
    private List<String> edgeList;

    @ApiModelProperty(value = "点的起始VID", example = "11", required = true)
    private String srcVid;

    @ApiModelProperty(value = "点的目的VID", example = "12", required = true)
    private String dstVid;

    @ApiModelProperty(value = "边edge的属性值集合", required = false)
    private List<Object> edgeValueList;
}
