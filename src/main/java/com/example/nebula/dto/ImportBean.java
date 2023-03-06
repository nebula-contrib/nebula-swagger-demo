package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImportBean {

    /**
     * 图谱标识
     */
    private String space;
    /**
     * 点集合
     */
    private List<VertexCombo> vertices;
    /**
     * 边集合
     */
    private List<EdgeCombo> edges;
}
