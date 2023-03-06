package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

@Data
public class VertexElement {
    /**
     * 元素名称
     */
    private String elementName;
    /**
     * 属性集合
     */
    private List<Property> properties;
}
