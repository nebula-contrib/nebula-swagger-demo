package com.example.nebula.dto;

import lombok.Data;

@Data
public class Property {
    /**
     * 属性名称
     */
    private String propName;
    /**
     * 对应CSV文件列号
     */
    private Integer csvIndex;
    /**
     * 属性类型
     */
    private String type;
}
