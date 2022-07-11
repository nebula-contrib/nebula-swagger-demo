package com.example.nebula.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 图可视化展示数据结构
 *
 * @author Li.Wei by 2022/3/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphData {
    private Collection<GraphNode> nodes;
    private Collection<GraphEdge> edges;
    private Collection<NodeType> NodeTypes;
}
