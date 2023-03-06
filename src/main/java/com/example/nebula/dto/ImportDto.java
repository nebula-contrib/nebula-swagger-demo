package com.example.nebula.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: ImportDto
 */
@Data
@ApiModel("数据导入入参")
public class ImportDto {

    @ApiModelProperty(value = "空间名称", example = "flceshi",required = true)
    private String space;

    @ApiModelProperty(value = "attribute可选值: tag标签/edge边类型",example = "tag",required = true)
    private String attribute;

    @ApiModelProperty(value = "属性名称", example = "t1",required = true)
    private String attributeName;

}
