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
@ApiModel("创建索引入参")
public class GraphCreateIndex {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称",example = "flceshi",required = true)
    private String space;

    @ApiModelProperty(value = "创建类型: tag/edge",example = "tag",required = true)
    private String type;

    @ApiModelProperty(value = "索引名称",example = "name_index",required = true)
    private String indexName;

    @ApiModelProperty(value = "tag/edge名称",example = "t1",required = true)
    private String tagEdgeName;

    @ApiModelProperty(value = "索引描述描述",example = "备注")
    private String comment;

    @ApiModelProperty(value = "属性集合")
    private List<AttributeBean> attributeBeanList;
}
