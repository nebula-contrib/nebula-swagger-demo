package com.example.nebula.dto.graph;

import cn.hutool.core.map.MapUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 图可视化-展示样式
 *
 * @author juwentao
 * @date 2022/5/9 15:44
 */
@Data
@Builder
@AllArgsConstructor
public class NodeType implements Serializable {
    private String id;
    private String label;
    private Map<String,String> style;

    public NodeType() {
        super();
    }

    public NodeType(String tagStart, String color) {
        this.id =tagStart;
        this.label =tagStart;
        HashMap<String, String> style = MapUtil.newHashMap();
        style.put("size","50");
        style.put("fill",color);
        this.style = style;
    }
}
