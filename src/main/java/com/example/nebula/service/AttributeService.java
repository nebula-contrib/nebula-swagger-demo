package com.example.nebula.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.nebula.constant.AttributeEnum;
import com.example.nebula.dto.graph.*;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.vo.AttributeVo;
import com.example.nebula.vo.CommonVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Descriptin: 属性操作
 * @ClassName: AttributeService
 */
@Service
public class AttributeService {


    @Autowired
    GraphCommonService graphCommonService;


    /**
     * @return java.util.List<com.hoteamsoft.common.vo.AttributeVo>
     * @Description 属性列表查询增加tag颜色, 增加tag子属性
     * @Param [graphShowAttribute]
     **/
    public List<AttributeVo> showAttribute(GraphShowAttribute graphShowAttribute) {
        List<AttributeVo> list = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);
        if (CollUtil.isNotEmpty(list)) {
            if (AttributeEnum.TAGS.name().equalsIgnoreCase(graphShowAttribute.getAttribute())) {
                //List<GraphTag> graphTagList = graphTagService.queryBySpace(graphShowAttribute.getSpace());
                //if (CollUtil.isNotEmpty(graphTagList)) {
                //    Map<String, GraphTag> tagMap = graphTagList.stream().collect(Collectors.toMap(GraphTag::getTagName, Function.identity()));
                list.forEach(attributeVo -> {
                    attributeVo.getData().forEach(dataBean -> {
                        String tag = dataBean.getRow().get(0);
                        //GraphTag graphTag = tagMap.get(tag);
                        //if (ObjectUtil.isNotNull(graphTag)) {
                        //    dataBean.getRow().add(graphTag.getColor());
                        //} else {
                        //    dataBean.getRow().add("");
                        //}
                        GraphShowInfo graphShowInfo = GraphShowInfo.builder().space(graphShowAttribute.getSpace())
                                .attribute("tag").attributeName(tag).build();
                        List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showAttributeInfo(graphShowInfo), AttributeVo.class);
                        AttributeVo attributeVoSon = attributeVoList.get(0);
                        dataBean.getRow().add(JSONUtil.toJsonStr(attributeVoSon));
                    });
                    attributeVo.getColumns().add("sonTagAttributeVo");
                });
                //}
            }
            if (AttributeEnum.EDGES.name().equalsIgnoreCase(graphShowAttribute.getAttribute())) {
                list.forEach(attributeVo -> {
                    attributeVo.getData().forEach(dataBean -> {
                        GraphShowInfo graphShowInfo = GraphShowInfo.builder().space(graphShowAttribute.getSpace())
                                .attribute("edge").attributeName(dataBean.getRow().get(0)).build();
                        List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showAttributeInfo(graphShowInfo), AttributeVo.class);
                        AttributeVo attributeVoSon = attributeVoList.get(0);
                        dataBean.getRow().add(JSONUtil.toJsonStr(attributeVoSon));
                    });
                    attributeVo.getColumns().add("sonEdgeAttributeVo");
                });
            }
        }
        return list;
    }


    /**
     * @return java.util.List<com.hoteamsoft.common.vo.CommonVo>
     * @Description 删除属性，如果有索引，将索引也删除
     * @Param [graphDropAttribute]
     **/
    public List<CommonVo> dropAttribute(GraphDropAttribute graphDropAttribute) {
        if (!"space".equalsIgnoreCase(graphDropAttribute.getAttribute())) {
            graphCommonService.executeJson(NebulaUtil.dropIndex(graphDropAttribute), CommonVo.class);
        }
        return graphCommonService.executeJson(NebulaUtil.dropAttribute(graphDropAttribute), CommonVo.class);
    }


    /**
     * @return com.github.pagehelper.PageInfo
     * @Description 属性分页查询
     * @Param [graphPageAttribute]
     **/
    public PageInfo<AttributeVo.DataBean> pageListAttribute(GraphPageAttribute graphPageAttribute) {
        List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showAttributes(new GraphShowAttribute(graphPageAttribute.getSpace(), graphPageAttribute.getAttribute())), AttributeVo.class);
        AttributeVo attributeVo = attributeVoList.get(0);

        List<AttributeVo.DataBean> data = attributeVo.getData();
        if (CollectionUtil.isNotEmpty(data)) {
            return startPage(data, graphPageAttribute.getPageNum(), graphPageAttribute.getPageSize());
        }
        return new PageInfo<>();
    }

    public <T> PageInfo<T> startPage(List<T> list, Integer pageNum, Integer pageSize) {
        //创建Page类
        Page page = new Page(pageNum, pageSize);
        //为Page类中的total属性赋值
        page.setTotal(list.size());
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(list.subList(startIndex, endIndex));
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    /**
     * @return java.util.List<com.hoteamsoft.common.vo.CommonVo>
     * @Description 增加属性的子属性
     * @Param [graphAddAttribute]
     **/
    public List<CommonVo> addAttributeProperty(GraphAddAttribute graphAddAttribute) {
        return graphCommonService.executeJson(NebulaUtil.addAttributeProperty(graphAddAttribute), CommonVo.class);
    }

    /**
     * @return java.util.List<com.hoteamsoft.common.vo.AttributeVo>
     * @Description 属性的子属性查询
     * @Param [graphShowInfo]
     **/
    public List<AttributeVo> showAttributeInfo(GraphShowInfo graphShowInfo) {
        List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showAttributeInfo(graphShowInfo), AttributeVo.class);
        //cover(attributeVoList);
        return attributeVoList;
    }

    private void cover(List<AttributeVo> attributeVoList) {
        if (CollectionUtil.isEmpty(attributeVoList)) {
            return;
        }
        AttributeVo attributeVo = attributeVoList.get(0);
        if (ObjectUtil.isNull(attributeVo)) {
            return;
        }
        List<AttributeVo.DataBean> data = (List<AttributeVo.DataBean>) attributeVo.getData();
        List<String> columns = attributeVo.getColumns();
        HashMap<String, List<String>> stringListHashMap = MapUtil.newHashMap();
        List<String> valueList = CollectionUtil.newArrayList();
        String key = columns.get(0);
        for (AttributeVo.DataBean datum : data) {
            String value = datum.getRow().get(0);
            valueList.add(value);
        }
        stringListHashMap.put(key, valueList);
        attributeVo.setFieldMap(stringListHashMap);
    }



    public List<AttributeVo> showCreateAttributeInfo(GraphShowInfo graphShowInfo) {
        List<AttributeVo> attributeVoList = graphCommonService.executeJson(NebulaUtil.showCreateAttributeInfo(graphShowInfo), AttributeVo.class);
        if (CollUtil.isNotEmpty(attributeVoList)) {
            for (AttributeVo attributeVo : attributeVoList) {
                for (AttributeVo.DataBean datum : attributeVo.getData()) {
                    attributeVo.getColumns().add("comment");
                    String row = datum.getRow().get(1);
                    String substring = row.substring(row.lastIndexOf(",") + 1);
                    if (StrUtil.isNotBlank(substring)) {
                        String replaceAll = substring.replace("=", ":");
                        String result = "{" + replaceAll + "}";
                        Map map = JSONUtil.toBean(JSONUtil.toJsonStr(result), Map.class);
                        if (map.containsKey("comment")) {
                            Object comment = map.get("comment");
                            datum.getRow().add(comment.toString());
                        } else {
                            datum.getRow().add("");
                        }
                    }
                    //List<GraphTag> graphTagList = graphTagService.queryBySpace(graphShowInfo.getSpace());
                    //if (CollUtil.isNotEmpty(graphTagList)) {
                    //    attributeVo.getColumns().add("color");
                    //    Map<String, GraphTag> tagMap = graphTagList.stream().collect(Collectors.toMap(GraphTag::getTagName, Function.identity()));
                    //    String tagName = datum.getRow().get(0);
                        //GraphTag graphTag = tagMap.get(tagName);
                        //if (ObjectUtil.isNotNull(graphTag)) {
                        //    datum.getRow().add(graphTag.getColor());
                        //} else {
                        //    datum.getRow().add("");
                        //}
                    //}
                }
            }
        }
        return attributeVoList;
    }
}
