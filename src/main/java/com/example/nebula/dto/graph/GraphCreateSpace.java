package com.example.nebula.dto.graph;

import cn.hutool.core.util.StrUtil;
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
@ApiModel("创建空间实体")
public class GraphCreateSpace {

    /**
     * 空间名称
     **/
    @ApiModelProperty(value = "空间名称", example = "flceshi")
    private String space;
    /**
     * 分片数量
     **/
    @ApiModelProperty(value = "分片数量", example = "1")
    private Integer partitionNum;
    /**
     * 类型
     **/
    @ApiModelProperty(value = "点类型:INT64,FIXED_STRING", example = "INT64")
    private String fixedType = "INT64";
    /**
     * 类型大小
     **/
    @ApiModelProperty(value = "类型长度,FIXED_STRING 此字段必填 如32,64")
    private String size = "";
    /**
     * 描述
     **/
    @ApiModelProperty(value = "描述")
    private String comment;

    public String getSize() {
        if (StrUtil.isNotBlank(size)) {
            return "(" + size + ")";
        }
        return "";
    }
}
