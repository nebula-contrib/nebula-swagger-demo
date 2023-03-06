package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

@Data
public class VertexCombo {
    /**
     * 文件id
     */
    private Integer fileId;
    /**
     * 点对应文件中的列号
     */
    private Integer vertexId;
    /**
     * 元素属性
     */
    private List<VertexElement> vertexElements;
}
