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
@ApiModel("修改空间备注入参实体")
public class GraphUpdateSpace {

    @ApiModelProperty(value = "空间名称", example = "flceshi",required = true)
    private String space;

    @ApiModelProperty(value = "空间中文名称", example = "空间中文名称",required = true)
    private String spaceChineseName;

    @ApiModelProperty(value = "空间备注", example = "备注信息",required = true)
    private String spaceComment;
}
