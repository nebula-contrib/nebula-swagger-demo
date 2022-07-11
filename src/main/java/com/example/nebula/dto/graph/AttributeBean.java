package com.example.nebula.dto.graph;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Descriptin:
 * @ClassName: AttributeBean
 */
@ApiModel("属性入参")
@Data
public class AttributeBean {

    /**
     * 属性名称
     **/
    @ApiModelProperty(value = "tag/edge的属性名称", example = "p3", required = true)
    private String propertyName;
    /**
     * 属性类型 (int bool string double .........)
     **/
    @ApiModelProperty(value = "索引长度:属性为string 必须有长度,其他类型不可传入 ", example = "30", required = true)
    private String indexLength;

    public String getPropertyName() {
        return "`"+propertyName +"`"+ getIndexLength();
    }

    private String getIndexLength() {
        if (StrUtil.isNotBlank(indexLength)) {
            return "(" + indexLength + ")";
        }
        return "";
    }

    private String getIndexFull() {
        if (StrUtil.isNotBlank(indexLength)) {
            return "(" + indexLength + ")";
        }
        return "";
    }
}
