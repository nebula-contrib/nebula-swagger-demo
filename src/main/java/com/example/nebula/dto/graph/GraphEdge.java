package com.example.nebula.dto.graph;

import lombok.*;

/**
 * 图可视化-边
 *
 * @author fulin by 2022/3/30
 */
@Data
@Builder
@EqualsAndHashCode(of = {"source", "target"})
@AllArgsConstructor
@NoArgsConstructor
public class GraphEdge {
    private String label;
    private String source;
    private String target;
    private Object edgeInfo;

    public GraphEdge(String edge, String tagStart, String tagEnd) {
        this.label = edge;
        this.source = tagStart;
        this.target = tagEnd;
    }
}
