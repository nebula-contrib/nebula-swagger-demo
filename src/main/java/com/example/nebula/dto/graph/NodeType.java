package com.example.nebula.dto.graph;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 图可视化-展示样式
 *
 * @author juwentao
 * @date 2022/5/9 15:44
 */
@Data
public class NodeType implements Serializable {
    private String id;
    private String label;
    private Map<String,String> style;

    public NodeType() {
        super();
    }
}
