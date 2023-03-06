package com.example.nebula.dto;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.nebula.constant.GqlConstant;
import com.example.nebula.dto.graph.GraphData;
import com.example.nebula.dto.graph.GraphEdge;
import com.example.nebula.dto.graph.GraphNode;
import com.example.nebula.dto.graph.NodeType;
import com.example.nebula.vo.AttributeVo;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fulin by 2022/3/30
 */
public class NebulaJsonConverter {

    /**
     *
     * @param jsonArray
     * @param attributeVos
     * @return
     */
    public static GraphData toGraphDataMain(JSONArray jsonArray, List<AttributeVo> attributeVos) {
        final JSONObject jsonObject = jsonArray.getJSONObject(0);
        final JSONArray data = jsonObject.getJSONArray("data");
        if (ObjectUtil.isNull(data)) {
            return new GraphData();
        }
        final Set<GraphNode> nodes = Sets.newHashSet();
        final Set<GraphEdge> edges = Sets.newHashSet();
        final List<NodeType> nodeTypeList = CollectionUtil.newArrayList();
        final Set<String> tagSet = CollectionUtil.newHashSet();

        //List<AttributeVo.DataBean> dataBeanList = attributeVos.get(0).getData();

        for (int d = 0; d < data.size(); d++) {
            final JSONObject dataJSONObject = data.getJSONObject(d);

            // meta 数据结构: [[]]
            final JSONArray meta = dataJSONObject.getJSONArray("meta");
            final JSONArray row = dataJSONObject.getJSONArray("row");

            for (int i = 0; i < meta.size(); i++) {
                final JSONArray metaJSONArray = meta.getJSONArray(i);
                final JSONArray rowJSONArray = row.getJSONArray(i);

                for (int i1 = 0; i1 < metaJSONArray.size(); i1++) {
                    final JSONObject oneMeta = metaJSONArray.getJSONObject(i1);
                    final JSONObject oneRow = rowJSONArray.getJSONObject(i1);
                    final String type = oneMeta.getString("type");
                    Object tagInfo = null;
                    Object edgeInfo = null;
                    switch (type) {
                        case "vertex":
                            String tag = GqlConstant.UNKNOWN;
                            String name = GqlConstant.UNKNOWN;
                            String color = "#289D73";
                            tagInfo = oneRow.entrySet();
                            for (Map.Entry<String, Object> entry : oneRow.entrySet()) {
                                final String key = entry.getKey();
                                if (key.endsWith("name")) {
                                    final Object value = entry.getValue();
                                    final String[] split = key.split("\\.");
                                    tag = split[0];
                                    name = value.toString();
                                    break;
                                }
                            }
                            String nodeTypes = GqlConstant.UNKNOWN;
                            if (ObjectUtil.notEqual(GqlConstant.UNKNOWN, tag)) {
                                nodeTypes = tag;
                            }
                            nodes.add(GraphNode.builder().id(oneMeta.getString("id")).tag(tag).label(name).nodeTypes(nodeTypes).tagInfo(tagInfo).build());


                            HashMap<String, String> colorMap = MapUtil.newHashMap();
                            colorMap.put("fill", color);
                            colorMap.put("size", "50");

                            //for (AttributeVo.DataBean dataBean : dataBeanList) {
                            //    List<String> row1 = dataBean.getRow();
                            //    if (CollectionUtil.isNotEmpty(row1)) {
                            //        String tagName = row1.get(0);
                            //        if (StrUtil.isNotBlank(tagName)) {
                            //            if (tagName.equalsIgnoreCase(tag) && row1.size() > 1) {
                            //                colorMap.put("fill", row1.get(1));
                            //                colorMap.put("size", row1.get(2));
                            //            }
                            //        }
                            //    }
                            //}

                            if (!tagSet.contains(tag)) {
                                nodeTypeList.add(NodeType.builder().id(oneMeta.getString("id")).label(tag).style(colorMap).build());
                            }
                            tagSet.add(tag);

                            break;
                        case "edge":
                            final JSONObject id = oneMeta.getJSONObject("id");
                            edgeInfo = oneRow.entrySet();
                            int typeFX = Integer.parseInt(id.get("type").toString());
                            if (typeFX > 0) {
                                edges.add(
                                        GraphEdge.builder()
                                                .source(id.getString("src")).target(id.getString("dst"))
                                                .label(id.getString("name")).edgeInfo(edgeInfo)
                                                .build()
                                );
                            }
                            if (typeFX < 0) {
                                edges.add(
                                        GraphEdge.builder()
                                                .source(id.getString("dst")).target(id.getString("src"))
                                                .label(id.getString("name")).edgeInfo(edgeInfo)
                                                .build()
                                );
                            }

                            break;
                        default:
                            throw new RuntimeException("type not found");
                    }
                }
            }
        }
        return GraphData.builder().nodes(nodes).edges(edges).NodeTypes(nodeTypeList).build();
    }
}

