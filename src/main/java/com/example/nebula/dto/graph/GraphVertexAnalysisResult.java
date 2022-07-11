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
@ApiModel("归因分析查询入参")
public class GraphVertexAnalysisResult {

    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "查询关键字集合",required = true)
    private List<String> wordList;
}
