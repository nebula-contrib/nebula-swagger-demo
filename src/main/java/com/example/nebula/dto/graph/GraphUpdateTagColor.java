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
@ApiModel("修改tag颜色入参")
public class GraphUpdateTagColor {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称",example = "flceshi")
    private String space;

    @ApiModelProperty(value = "类型: tag/edge",example = "tag")
    private String type;

    @ApiModelProperty(value = "tag/edge名称",example = "demo")
    private String tagEdgeName;

    @ApiModelProperty(value = "颜色")
    private String color;
}
