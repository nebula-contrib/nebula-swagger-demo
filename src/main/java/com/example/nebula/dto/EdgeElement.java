package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

@Data
public class EdgeElement {
    /**
     * 边名称
     */
    private String elementName;
    /**
     * 起点对应的列号
     */
    private Integer srcId;
    /**
     * 终点对应的列号
     */
    private Integer dstId;

    private Integer rank;
    /**
     * 属性集合
     */
    private List<Property> properties;
}
