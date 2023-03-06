package com.example.nebula.dto.graph;

import lombok.*;

/**
 * 图可视化-节点
 *
 * @author fulin by 2022/3/30
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class GraphNode {
    private String id;
    private String tag;
    private String label;
    private String nodeTypes;
    private Object tagInfo;

    public GraphNode(String tag, String tagInfo) {
        this.id = tag;
        this.tag = tag;
        this.label = tag;
        this.nodeTypes = tag;
        this.tagInfo = tagInfo;
    }
}
