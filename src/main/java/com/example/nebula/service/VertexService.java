package com.example.nebula.service;


import com.example.nebula.dto.NebulaVertexJsonResult;
import com.example.nebula.dto.graph.GraphExpand;
import com.example.nebula.dto.graph.GraphVertexTatAttributeQuery;
import com.example.nebula.dto.graph.GraphVertexTatsQuery;
import com.example.nebula.util.NebulaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descriptin: 点实现类
 * @ClassName: VertexService
 */
@Service
@Slf4j
public class VertexService {

    @Autowired
    GraphCommonService graphCommonService;


    public List<NebulaVertexJsonResult> vertexList(String space) {

        // 查询当前的所有tag
        //List<String> tagList = CollectionUtil.newArrayList();
        //GraphShowAttribute graphShowAttribute = new GraphShowAttribute(space, AttributeEnum.TAGS.name());
        //List<AttributeVo> list = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);
        //AttributeVo attributeVo = list.get(0);
        //List<AttributeVo.DataBean> data = attributeVo.getData();
        //if (CollectionUtil.isNotEmpty(data)) {
        //    for (AttributeVo.DataBean datum : data) {
        //        String tag = datum.getRow().get(0);
        //        tagList.add(tag);
        //    }
        //}
        //log.info("查询到的所有tag: {}", JSONUtil.toJsonPrettyStr(tagList));
        //if (CollectionUtil.isEmpty(tagList)) {
        //    return CollectionUtil.newArrayList();
        //}
        //return graphCommonService.executeJson(NebulaUtil.queryMatch(tagList,space), NebulaVertexJsonResult.class);

        return graphCommonService.executeJson(NebulaUtil.queryMatch(space), NebulaVertexJsonResult.class);
    }

    public List<NebulaVertexJsonResult> vertexExpandQuery(GraphExpand graphExpand) {
        String vidType = graphCommonService.getVidType(graphExpand.getSpace());
        return graphCommonService.executeJson(NebulaUtil.expandQuery(graphExpand,vidType), NebulaVertexJsonResult.class);
    }

    public List<NebulaVertexJsonResult> vertexTagsQuery(GraphVertexTatsQuery graphVertexTatsQuery) {
        String vidType = graphCommonService.getVidType(graphVertexTatsQuery.getSpace());
        return graphCommonService.executeJson(NebulaUtil.vertexTagsQuery(graphVertexTatsQuery,vidType), NebulaVertexJsonResult.class);
    }

    public List<NebulaVertexJsonResult> vertexTagAttributeQuery(GraphVertexTatAttributeQuery graphVertexTatAttributeQuery) {
        return graphCommonService.executeJson(NebulaUtil.vertexTagAttributeQuery(graphVertexTatAttributeQuery), NebulaVertexJsonResult.class);
    }
}
