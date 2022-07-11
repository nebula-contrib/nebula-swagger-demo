package com.example.nebula.dto.graph;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关系绑定入参
 *
 * @ClassName: GraphRelationInsert
 */
@Data
public class GraphRelationInsert {

    @ApiModelProperty(value = "空间名称", example = "movies", required = true)
    private String space;

    @ApiModelProperty(value = "开始标签", required = true)
    private String tagStart;

    @ApiModelProperty(value = "边类型", required = true)
    private String edge;

    @ApiModelProperty(value = "结束标签", required = true)
    private String tagEnd;
}
