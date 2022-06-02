package com.example.nebula.dto.graph;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("根据tag标签查询点入参实体")
public class GraphVertexTatsQuery {

    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "标签集合", required = true)
    private List<String> tagList;

    @ApiModelProperty(value = "结果条数", example = "100", required = true)
    @Max(Integer.MAX_VALUE)
    @Min(1)
    private Integer resultSize = Integer.MAX_VALUE;
}
