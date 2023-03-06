package com.example.nebula.constant;


import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum EdgeDirectionEnum {


    流出("OUTFLOW", "", ">","OUT"),
    流入("INFLOW", "<", "","IN"),
    双向("TWO-WAY", "", "","BOTH");

    private String name;
    private String left;
    private String right;
    private String direct;


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

    public static String getDirection(String name) {
        EdgeDirectionEnum[] values = EdgeDirectionEnum.values();
        for (EdgeDirectionEnum edgeDirectionEnum : values) {
            if (name.equalsIgnoreCase(edgeDirectionEnum.getName())) {
                return edgeDirectionEnum.getDirect();
            }
        }
        return "BOTH";
    }

}
