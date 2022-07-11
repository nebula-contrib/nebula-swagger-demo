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
@ApiModel("查询边分页入参")
public class GraphPageEdge extends PageBeanDto {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "边类型edge", required = false)
    private String edge;
}









