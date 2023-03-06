package com.example.nebula.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.nebula.dto.NebulaVertexJsonResult;
import com.example.nebula.dto.graph.GraphExpand;
import com.example.nebula.dto.graph.GraphSpace;
import com.example.nebula.dto.graph.GraphVertexTatAttributeQuery;
import com.example.nebula.dto.graph.GraphVertexTatsQuery;
import com.example.nebula.util.NebulaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        return graphCommonService.executeJson(NebulaUtil.expandQuery(graphExpand, vidType), NebulaVertexJsonResult.class);
    }

    /**
     * tag查询
     * @param graphVertexTatsQuery
     * @return
     */
    public List<NebulaVertexJsonResult> vertexTagsQuery(GraphVertexTatsQuery graphVertexTatsQuery) {
        String vidType = graphCommonService.getVidType(graphVertexTatsQuery.getSpace());
        return graphCommonService.executeJson(NebulaUtil.vertexTagsQuery(graphVertexTatsQuery, vidType), NebulaVertexJsonResult.class);
    }

    /**
     * tag根据属性查询
     *
     * @param graphVertexTatAttributeQuery
     * @return
     */
    public List<NebulaVertexJsonResult> vertexTagAttributeQuery(GraphVertexTatAttributeQuery graphVertexTatAttributeQuery) {
        return graphCommonService.executeJson(NebulaUtil.vertexTagAttributeQuery(graphVertexTatAttributeQuery), NebulaVertexJsonResult.class);
    }

    /**
     * 点分页查询
     *
     * @param graphVertexTatsQuery
     * @return
     */
    public Map<String, Object> vertexPage(GraphVertexTatsQuery graphVertexTatsQuery) {
        List<NebulaVertexJsonResult> list;
        List<NebulaVertexJsonResult> countList;
        if (StrUtil.isNotBlank(graphVertexTatsQuery.getTag()) || ObjectUtil.isNotNull(graphVertexTatsQuery.getPointKey())) {
            String vidType = graphCommonService.getVidType(graphVertexTatsQuery.getSpace());
            list = graphCommonService.executeJson(NebulaUtil.vertexTagsQueryPage(graphVertexTatsQuery, vidType), NebulaVertexJsonResult.class);
            countList = graphCommonService.executeJson(NebulaUtil.vertexTagsQuery(graphVertexTatsQuery, vidType), NebulaVertexJsonResult.class);
        } else {
            GraphSpace graphSpace = new GraphSpace();
            BeanUtil.copyProperties(graphVertexTatsQuery, graphSpace);
            list = graphCommonService.executeJson(NebulaUtil.vertexPage(graphSpace), NebulaVertexJsonResult.class);
            countList = graphCommonService.executeJson(NebulaUtil.queryMatch(graphVertexTatsQuery.getSpace()), NebulaVertexJsonResult.class);
        }

        int size = countList.get(0).getData().size();
        Map<String, Object> result = MapUtil.newHashMap();
        result.put("list", list);
        result.put("count", size);
        return result;
    }

    /**
     * 获取随机点列表
     *
     * @param graphSpace
     * @return
     */
    public List<NebulaVertexJsonResult> randomList(GraphSpace graphSpace) {

        String space = graphSpace.getSpace();
        List<NebulaVertexJsonResult> list = graphCommonService.executeJson(NebulaUtil.queryMatchLimit(space), NebulaVertexJsonResult.class);
        NebulaVertexJsonResult nebulaVertexJsonResult = list.get(0);
        List<NebulaVertexJsonResult.OneData> nebulaVertexJsonResultData = nebulaVertexJsonResult.getData();

        nebulaVertexJsonResultData = RandomUtil.randomEleList(nebulaVertexJsonResultData, 10);
        nebulaVertexJsonResult.setData(nebulaVertexJsonResultData);
        return list;
    }
}
