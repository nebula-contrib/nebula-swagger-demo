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
@ApiModel("删除边入参")
public class GraphDeleteEdge {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "边类型edge名称", example = "e1", required = true)
    private String edgeName;

    @ApiModelProperty(value = "点的起始VID", example = "11", required = true)
    private String srcVid;

    @ApiModelProperty(value = "点的目的VID", example = "12", required = true)
    private String dstVid;

}
