package com.example.nebula.dto.graph;

import cn.hutool.core.util.StrUtil;
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

    @ApiModelProperty(value = "标签", example = "t1", required = true)
    private String tag;

    @ApiModelProperty(value = "执行条件", required = true)
    private String condition;

    @ApiModelProperty(value = "结果条数", example = "100", required = true)
    @Max(Integer.MAX_VALUE)
    @Min(1)
    private Integer resultSize = Integer.MAX_VALUE;


    public String getCondition() {
        if (StrUtil.isNotBlank(condition)) {
            return " where " + condition;
        }
        return "";
    }
}
