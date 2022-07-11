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
@ApiModel("解析文字入参")
public class GraphVertexAnalysisWord {

    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "查询关键字",required = true)
    private String word;
}
