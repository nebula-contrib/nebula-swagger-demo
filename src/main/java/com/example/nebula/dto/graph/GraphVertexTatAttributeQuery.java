package com.example.nebula.dto.graph;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("根据tag标签查询点入参实体")
public class GraphVertexTatAttributeQuery {

    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "标签",example = "t1",required = true)
    private String tag;

    @ApiModelProperty(value = "标签属性名称",example = "p3",required = true)
    private String tagName;

    @ApiModelProperty(value = "执行条件: EQUAL(相等);CONTAINS(包含);STARTS(开始);ENDS(结束)", example = "CONTAINS",required = true)
    private String condition;

    @ApiModelProperty(value = "标签属性的值",example = "值",required = true)
    private String tagValue;

    @ApiModelProperty(value = "结果条数", example = "100", required = true)
    @Max(Integer.MAX_VALUE)
    @Min(1)
    private Integer resultSize = Integer.MAX_VALUE;
}
