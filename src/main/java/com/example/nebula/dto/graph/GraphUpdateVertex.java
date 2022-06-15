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
@ApiModel("修改点入参")
public class GraphUpdateVertex {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "标签tag名称", example = "t1", required = true)
    private String tagName;

    @ApiModelProperty(value = "标签tag属性集合", required = true)
    private List<String> tagList;
    /**
     * point的key
     **/
    @ApiModelProperty(value = "点的VID", example = "11", required = true)
    private String pointKey;

    @ApiModelProperty(value = "标签tag的属性值集合", required = true)
    private List<Object> tagValueList;
}
