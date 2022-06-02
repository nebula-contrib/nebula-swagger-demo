package com.example.nebula.constant;


import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum EdgeDirectionEnum {


    流出("OUTFLOW", "", ">"),
    流入("INFLOW", "<", ""),
    双向("TWO-WAY", "", "");

    private String name;
    private String left;
    private String right;


    /**
     * @Description 获取方向
     * @Param [name]
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> getLeftAndRight(String name) {
        EdgeDirectionEnum[] values = EdgeDirectionEnum.values();
        List<String> results = CollectionUtil.newArrayList();
        for (EdgeDirectionEnum edgeDirectionEnum : values) {
            if (name.equalsIgnoreCase(edgeDirectionEnum.getName())) {
                results.add(edgeDirectionEnum.getLeft());
                results.add(edgeDirectionEnum.getRight());
                return results;
            }
        }
        results.add("");
        results.add("");
        return results;
    }
}
