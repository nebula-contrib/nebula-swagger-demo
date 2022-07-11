package com.example.nebula.dto.graph;

import com.example.nebula.dto.PageBeanDto;
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
@ApiModel("查询属性分页查询入参")
public class GraphPageAttribute extends PageBeanDto {

    @ApiModelProperty(value = "空间名称",example = "flceshi",required = true)
    private String space;

    @ApiModelProperty(value = "attribute可选值:tags标签/edges边类型",example = "tags",required = true)
    private String attribute;

}
