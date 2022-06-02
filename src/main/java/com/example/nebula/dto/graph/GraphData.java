package com.example.nebula.dto.graph;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * 图可视化展示数据结构
 *
 * @author fulin by 2022/3/30
 */
@Data
@Builder
public class GraphData {
    private Collection<GraphNode> nodes;
    private Collection<GraphEdge> edges;
    private Collection<NodeType> NodeTypes;
}
