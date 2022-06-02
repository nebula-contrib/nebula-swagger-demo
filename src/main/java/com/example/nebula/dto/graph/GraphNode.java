package com.example.nebula.dto.graph;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 图可视化-节点
 *
 * @author fulin by 2022/3/30
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class GraphNode {
    private String id;
    private String tag;
    private String label;
    private String nodeTypes;
}
