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
@ApiModel("全文检索")
public class GraphVertexFullQuery {

    @ApiModelProperty(value = "空间名称", example = "movies", required = true)
    private String space;

    @ApiModelProperty(value = "查询关键字",required = true)
    private String word;

    @ApiModelProperty(value = "标签集合",required = false)
    private List<String> tagList;

    @ApiModelProperty(value = "查询最大条数",required = true)
    private Integer resultSize;
}
