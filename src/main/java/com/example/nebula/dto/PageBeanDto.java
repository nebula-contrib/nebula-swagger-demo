package com.example.nebula.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Descriptin: 分页参数入参
 * @ClassName: PageBeanDto
 */
@ApiModel("分页参数入参")
@Data
public class PageBeanDto {

    @ApiModelProperty(value = "页码:从1开始",example = "1",required = false)
    private Integer pageNum = 1;

    @ApiModelProperty(value = "分页条数",example = "10",required = false)
    private Integer pageSize = 10;
}
