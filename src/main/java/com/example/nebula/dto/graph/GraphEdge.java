package com.example.nebula.dto.graph;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图可视化-边
 *
 * @author fulin by 2022/3/30
 */
@Data
@Builder
@EqualsAndHashCode(of = {"source", "target"})
public class GraphEdge {
    private String label;
    private String source;
    private String target;
}
