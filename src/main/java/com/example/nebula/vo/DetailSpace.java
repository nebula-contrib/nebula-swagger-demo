package com.example.nebula.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Descriptin: 图空间详情
 * @ClassName: DetailSpace
 */
@ApiModel("图空间详情实体")
@Data
public class DetailSpace {

    @ApiModelProperty("空间名称")
    private String space;

    @ApiModelProperty("标签个数")
    private Integer tagsNum;

    @ApiModelProperty("边类型个数")
    private Integer edgesNum;

    //@ApiModelProperty("空间备注")
    //private String spaceComment;

}
