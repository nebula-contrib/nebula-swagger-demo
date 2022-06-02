package com.example.nebula.dto;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Nebula-Graph session json 查询返回结果反序列化。
 * 该序列化非通用模式，需要具体分析 json 结果。
 *
 * @author fulin by 2022/3/29
 */
@Data
@ApiModel("返回类")
public class NebulaVertexJsonResult {
    @ApiModelProperty(value = "空间名称")
    private String spaceName;
    @ApiModelProperty(value = "潜伏期")
    private int latencyInUs;
    @ApiModelProperty(value = "数据集合")
    private List<OneData> data;
    @ApiModelProperty(value = "字段")
    private List<String> columns;

    public Set<FailureTroubleshootingVo> toFailureTroubleshootingVos() {
        final List<OneData> data = this.getData();
        final Set<FailureTroubleshootingVo> r = Sets.newHashSetWithExpectedSize(data.size());
        for (OneData oneData : data) {
            final List<OneMeta> meta = oneData.getMeta();
            final List<LinkedHashMap<String, String>> row = oneData.getRow();
            for (int i = 0; i < meta.size(); i++) {
                final Map<String, String> oneRow = row.get(i);
                final Map<String, String> properties = Maps.newHashMapWithExpectedSize(oneRow.size());
                String tag = "unknown";
                for (Map.Entry<String, String> entry : oneRow.entrySet()) {
                    final String key = entry.getKey();
                    final String[] split = key.split("\\.");
                    tag = split[0];
                    properties.put(split[1], entry.getValue());
                }
                r.add(FailureTroubleshootingVo.builder()
                    .id(meta.get(i).getId())
                    .tag(tag)
                    .properties(properties)
                    .build());
            }
        }
        return r;
    }

    @Data
    public static class OneData {
        @ApiModelProperty(value = "元数据")
        private List<OneMeta> meta;
        @ApiModelProperty(value = "属性名称: 属性值")
        private List<LinkedHashMap<String, String>> row;
    }

    @Data
    public static class OneMeta {
        @ApiModelProperty(value = "id")
        private String id;
        @ApiModelProperty(value = "类型")
        private String type;
    }
}

