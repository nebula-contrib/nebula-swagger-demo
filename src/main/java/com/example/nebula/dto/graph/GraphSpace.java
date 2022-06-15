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
@ApiModel("通用入参实体")
public class GraphSpace extends PageBeanDto {

    @ApiModelProperty(value = "空间名称", example = "flceshi",required = true)
    private String space;
}
