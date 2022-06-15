package com.example.nebula.util;


import cn.hutool.core.collection.CollectionUtil;
import com.example.nebula.constant.AttributeEnum;
import com.example.nebula.constant.ConditionEnum;
import com.example.nebula.constant.EdgeDirectionEnum;
import com.example.nebula.dto.graph.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NebulaUtil {


    /**
     * @return java.lang.String
     * @Description 查询 spaces/tags/edges
     * @Param [GraphShowAttribute]
     **/
    public static String showAttributes(GraphShowAttribute graphShowAttribute) {

        String showSpaces = "";
        if (AttributeEnum.SPACES.name().equalsIgnoreCase(graphShowAttribute.getAttribute())) {
            showSpaces = String.format("SHOW %s;", graphShowAttribute.getAttribute());
        } else {
            showSpaces = String.format("USE `%s`;SHOW %s;", graphShowAttribute.getSpace(), graphShowAttribute.getAttribute());
        }

        log.info("查询{}-gql语句:{}", graphShowAttribute.getAttribute(), showSpaces);
        return showSpaces;
    }


    /**
     * @return java.lang.String
     * @Description 查询 tag/edge 索引
     * @Param [GraphShowIndex]
     **/
    public static String showIndexes(GraphShowIndex graphShowIndex) {
        String showIndexes = String.format("USE `%s`;SHOW %s INDEXES;", graphShowIndex.getSpace(), graphShowIndex.getAttribute());
        log.info("查询{}-gql语句:{}", graphShowIndex.getAttribute(), showIndexes);
        return showIndexes;
    }

    /**
     * @return java.lang.String
     * @Description 创建空间
     * @Param [graphCreateSpace]
     **/
    public static String createSpace(GraphCreateSpace graphCreateSpace) {
        String createSpace = String.format("CREATE SPACE `%s` (partition_num = %s, vid_type = %s %s) COMMENT = '%s'",
            graphCreateSpace.getSpace(), graphCreateSpace.getPartitionNum(), graphCreateSpace.getFixedType(),
            graphCreateSpace.getSize(), graphCreateSpace.getComment());
        log.info("创建空间-gql语句:{}", createSpace);
        return createSpace;
    }

    /**
     * @return java.lang.String
     * @Description 切换空间
     * @Param [spaceName]
     **/
    public static String useSpace(String spaceName) {
        String useSpace = String.format("USE `%s`;", spaceName);
        log.info("切换空间-gql语句:{}", useSpace);
        return useSpace;
    }

    /**
     * @return java.lang.String
     * @Description 空间信息
     * @Param [spaceName]
     **/
    public static String spaceInfo(String spaceName) {
        String spaceInfo = String.format("DESCRIBE SPACE `%s`;", spaceName);
        log.info("空间信息-gql语句:{}", spaceInfo);
        return spaceInfo;
    }


    /**
     * @return java.lang.String
     * @Description 创建tag
     * @Param [graphCreateTag]
     **/
    public static String createTagEdge(GraphCreateTagEdge graphCreateTagEdge) {
        StringBuffer stringBuffer = new StringBuffer();
        List<PropertyBean> propertyList = graphCreateTagEdge.getPropertyList();
        if (CollectionUtil.isNotEmpty(propertyList)) {
            for (int i = 0; i < propertyList.size(); i++) {

                PropertyBean propertyBean = propertyList.get(i);
                stringBuffer.append(" `" + propertyBean.getPropertyName() + "` " + propertyBean.getPropertyType() + " " +
                    "" + propertyBean.getIsNull() + " " + propertyBean.getDefaultValue() + " COMMENT '" + propertyBean.getPropertyComment() + "' ");
                if (propertyList.size() > 1 && (i + 1) != propertyList.size()) {
                    stringBuffer.append(",");
                }
            }
        }
        String bufferString = stringBuffer.toString();
        log.info("stringBuffer :{}", bufferString);

        String createTag = String.format("USE `%s`;CREATE %s `%s` ( " + bufferString + " )  COMMENT = '%s' ",
            graphCreateTagEdge.getSpace(), graphCreateTagEdge.getType(), graphCreateTagEdge.getTagEdgeName(), graphCreateTagEdge.getTagEdgeComment());
        log.info("创建Tag-gql语句:{}", createTag);
        return createTag;
    }

    /**
     * @return java.lang.String
     * @Description 创建点
     * @Param [graphCreateVertex]
     **/
    public static String createPoint(GraphCreateVertex graphCreateVertex) {
        List<Object> tagValueList = graphCreateVertex.getTagValueList();
        StringBuffer stringBuffer = getStringBuffer(tagValueList);
        String bufferString = stringBuffer.toString();
        log.info("stringBuffer :{}", bufferString);

        StringBuffer stringBufferTagList = new StringBuffer();
        List<String> tagList = graphCreateVertex.getTagList();
        if (CollectionUtil.isNotEmpty(tagList)) {

            for (int i = 0; i < tagList.size(); i++) {
                String tagPropertyName = tagList.get(i);
                stringBufferTagList.append(" `" + tagPropertyName + "` ");
                if (tagList.size() > 1 && (i + 1) != tagList.size()) {
                    stringBufferTagList.append(",");
                }
            }
        }

        String createPoint = String.format("USE `%s`;INSERT VERTEX IF NOT EXISTS `%s`(%s) VALUES '%s': (" + bufferString + ");"
            , graphCreateVertex.getSpace(), graphCreateVertex.getTagName(), stringBufferTagList.toString(),
            graphCreateVertex.getPointKey());
        log.info("创建vertex-gql语句:{}", createPoint);
        return createPoint;
    }

    /**
     * @return java.lang.String
     * @Description 删除属性
     * @Param [graphCreatePoint]
     **/
    public static String dropAttribute(GraphDropAttribute graphDropAttribute) {
        String dropAttribute = String.format("USE `%s`;DROP %s IF EXISTS `%s`;", graphDropAttribute.getSpace(), graphDropAttribute.getAttribute(), graphDropAttribute.getAttributeName());
        log.info("删除属性-gql语句:{}", dropAttribute);
        return dropAttribute;
    }

    /**
     * @return java.lang.String
     * @Description 删除索引
     * @Param [graphDropAttribute]
     **/
    public static String dropIndex(GraphDropAttribute graphDropAttribute) {
        String dropIndex = String.format("USE `%s`;DROP %s INDEX IF EXISTS `%s`;", graphDropAttribute.getSpace(), graphDropAttribute.getAttribute(), graphDropAttribute.getAttributeName());
        log.info("删除索引-gql语句:{}", dropIndex);
        return dropIndex;
    }

    /**
     * @return java.lang.String
     * @Description 增加某个属性的 子属性
     * @Param [graphAddAttribute]
     **/
    public static String addAttributeProperty(GraphAddAttribute graphAddAttribute) {
        String addAttributeProperty = String.format("USE `%s`;ALTER %s `%s` ADD (`%s` %s %s %s %s);"
            , graphAddAttribute.getSpace(), graphAddAttribute.getAttribute(), graphAddAttribute.getAttributeName(),
            graphAddAttribute.getPropertyName(), graphAddAttribute.getPropertyType(),
            graphAddAttribute.getIsNull(), graphAddAttribute.getDefaultValue(), graphAddAttribute.getCommon());
        log.info("增加某个属性的 子属性 -gql语句:{}", addAttributeProperty);
        return addAttributeProperty;
    }


    /**
     * @return java.lang.String
     * @Description 删除某个属性的子属性
     * @Param [graphDelAttribute]
     **/
    public static String delAttributeProperty(GraphDelAttribute graphDelAttribute) {

        StringBuffer stringBuffer = new StringBuffer();
        List<String> propertyNameList = graphDelAttribute.getPropertyNameList();
        for (int i = 0; i < propertyNameList.size(); i++) {
            String value = propertyNameList.get(i);
            stringBuffer.append("`" + value + "`");
            if (propertyNameList.size() > 1 && (i + 1) != propertyNameList.size()) {
                stringBuffer.append(",");
            }
        }

        String delAttributeProperty = String.format("USE `%s`; ALTER %s `%s` DROP (%s);"
            , graphDelAttribute.getSpace(), graphDelAttribute.getAttribute(),
            graphDelAttribute.getAttributeName(), stringBuffer.toString());
        log.info("删除某个属性的 子属性 -gql语句:{}", delAttributeProperty);
        return delAttributeProperty;
    }

    /**
     * @return java.lang.String
     * @Description 查询属性的 子属性列表
     * @Param [graphShowInfo]
     **/
    public static String showAttributeInfo(GraphShowInfo graphShowInfo) {
        String delAttributeProperty = String.format("USE `%s`; DESCRIBE %s `%s`;"
            //String delAttributeProperty = String.format("USE `%s`; SHOW CREATE %s `%s`;"
            , graphShowInfo.getSpace(), graphShowInfo.getAttribute(), graphShowInfo.getAttributeName());
        log.info("查询属性的 子属性列表 -gql语句:{}", delAttributeProperty);
        return delAttributeProperty;
    }

    /**
     * @return java.lang.String
     * @Description 根据某个空间下的tag标签查询所有的vertex点
     * @Param [tagList, space] 标签集合, 空间名称
     **/
    public static String queryMatch(List<String> tagList, String space) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < tagList.size(); i++) {
            String tag = tagList.get(i);
            stringBuffer.append("MATCH (v:`" + tag + "`) RETURN v limit " + Integer.MAX_VALUE + "");
            if (tagList.size() > 1 && (i + 1) != tagList.size()) {
                stringBuffer.append(" UNION ");
            }
        }
        String bufferString = stringBuffer.toString();
        log.info("bufferString: {}", bufferString);
        String queryMatch = String.format("USE `%s`; " + bufferString + "", space);
        log.info("match查询 -gql语句:{}", queryMatch);
        return queryMatch;
    }

    public static String queryMatch(String space) {
        String queryMatch = String.format("USE `%s`; match (v) return v limit " + Integer.MAX_VALUE + ";", space);
        log.info("match查询 -gql语句:{}", queryMatch);
        return queryMatch;
    }


    /**
     * @return java.lang.String
     * @Description 点删除
     * @Param [graphDeleteVertex]
     **/
    public static String deleteVertex(GraphDeleteVertex graphDeleteVertex) {
        String deleteVertex = String.format("USE `%s`; DELETE VERTEX '%s';", graphDeleteVertex.getSpace(), graphDeleteVertex.getVid());
        log.info("vertex点删除 -gql语句:{}", deleteVertex);
        return deleteVertex;
    }


    /**
     * @return java.lang.String
     * @Description vertex点删除后删除出入边
     * @Param [graphDeleteVertex]
     **/
    public static String deleteVertexEdge(GraphDeleteVertex graphDeleteVertex) {
        String deleteVertexEdge = String.format("USE `%s`; DELETE VERTEX '%s' WITH EDGE;", graphDeleteVertex.getSpace(), graphDeleteVertex.getVid());
        log.info("vertex点删除后删除出入边 -gql语句:{}", deleteVertexEdge);
        return deleteVertexEdge;
    }


    /**
     * @return java.lang.String
     * @Description 插入边(绑两点之的关系)
     * @Param [graphInsertEdge]
     **/
    public static String insertEdge(GraphInsertEdge graphInsertEdge) {
        List<Object> edgeValueList = graphInsertEdge.getEdgeValueList();
        StringBuffer stringBuffer = getStringBuffer(edgeValueList);
        String bufferString = stringBuffer.toString();
        log.info("stringBuffer :{}", bufferString);

        String insertEdge = String.format("USE `%s`; INSERT EDGE IF NOT EXISTS `%s` (%s) VALUES '%s'->'%s':( %s );"
            , graphInsertEdge.getSpace(), graphInsertEdge.getEdgeName(), Joiner.on(",").join(graphInsertEdge.getEdgeList()),
            graphInsertEdge.getSrcVid(), graphInsertEdge.getDstVid(), bufferString);
        log.info("插入边 -gql语句:{}", insertEdge);
        return insertEdge;
    }

    /**
     * @return java.lang.StringBuffer
     * @Description 获取一个拼接好的字符串 "n1", 1
     * @Param [edgeValueList]
     **/
    private static StringBuffer getStringBuffer(List<Object> edgeValueList) {
        StringBuffer stringBuffer = new StringBuffer();
        if (CollectionUtil.isNotEmpty(edgeValueList)) {
            //stringBuffer.append("(");
            for (int i = 0; i < edgeValueList.size(); i++) {
                Object value = edgeValueList.get(i);
                if (value instanceof String) {
                    stringBuffer.append("'" + value + "'");
                } else {
                    stringBuffer.append(value);
                }
                if (edgeValueList.size() > 1 && (i + 1) != edgeValueList.size()) {
                    stringBuffer.append(",");
                }
            }
            //stringBuffer.append(")");
        }
        return stringBuffer;
    }

    /**
     * @return java.lang.String
     * @Description 删除边, 解除点的绑定关系
     * @Param [graphDeleteEdge]
     **/
    public static String deleteEdge(GraphDeleteEdge graphDeleteEdge) {
        String deleteEdge = String.format("USE `%s`; DELETE EDGE %s '%s' -> '%s' @0;"
            , graphDeleteEdge.getSpace(), graphDeleteEdge.getEdgeName(), graphDeleteEdge.getSrcVid(), graphDeleteEdge.getDstVid());
        log.info("删除边 -gql语句:{}", deleteEdge);
        return deleteEdge;
    }

    /**
     * @return java.lang.String
     * @Description 查询创建的边
     * @Param [graphSpace]
     **/
    public static String listEdge(GraphSpace graphSpace) {
        String listEdge = String.format("USE `%s`;MATCH ()<-[e]-()RETURN e LIMIT " + Integer.MAX_VALUE + " ;", graphSpace.getSpace());
        log.info("查询创建的边 -gql语句:{}", listEdge);
        return listEdge;
    }

    /**
     * @return java.lang.String
     * @Description 扩展查询
     * @Param [graphExpand]
     **/
    public static String expandQuery(GraphExpand graphExpand) {
        StringBuffer stringBuffer = new StringBuffer();
        List<String> edgeList = graphExpand.getEdgeList();
        for (int i = 0; i < edgeList.size(); i++) {
            String edge = edgeList.get(i);
            stringBuffer.append(":");
            stringBuffer.append("`").append(edge).append("`");
            if (edgeList.size() > 1 && (i + 1) != edgeList.size()) {
                stringBuffer.append("|");
            }
        }
        String bufferString = stringBuffer.toString();
        log.info("bufferString:{}", bufferString);

        List<String> leftAndRight = EdgeDirectionEnum.getLeftAndRight(graphExpand.getDirection());

        String expandQuery = String.format("USE `%s`;MATCH p=(v) %s- [e " + bufferString + " * %s %s]-%s (v2) WHERE id(v) IN [%s] RETURN p LIMIT " + graphExpand.getResultSize() + ";",
            graphExpand.getSpace(), leftAndRight.get(0), graphExpand.getStepStart(), graphExpand.getStepEndResult(), leftAndRight.get(1), graphExpand.getVidList());
        log.info("扩展查询 -gql语句:{}", expandQuery);
        return expandQuery;
    }

    /**
     * @return java.lang.String
     * @Description 根据tag标签查询点
     * @Param [graphVertexTatsQuery]
     **/
    public static String vertexTagsQuery(GraphVertexTatsQuery graphVertexTatsQuery) {
        String vertexTagsQuery = String.format("USE `%s`;MATCH p=(v:`%s`) return v limit " + graphVertexTatsQuery.getResultSize() + ";", graphVertexTatsQuery.getSpace(),
            Joiner.on(":").join(graphVertexTatsQuery.getTagList()));
        log.info("根据tag标签查询点 -gql语句:{}", vertexTagsQuery);
        return vertexTagsQuery;
    }

    /**
     * @return java.lang.String
     * @Description 根据tag标签属性查询点
     * @Param [graphVertexTatAttributeQuery]
     **/
    public static String vertexTagAttributeQuery(GraphVertexTatAttributeQuery graphVertexTatAttributeQuery) {
        String vertexTagsQuery = String.format("USE `%s`;match p=(v:`%s`) where v.`%s`.`%s` %s '%s' return v limit " + graphVertexTatAttributeQuery.getResultSize() + ";"
            , graphVertexTatAttributeQuery.getSpace(), graphVertexTatAttributeQuery.getTag(), graphVertexTatAttributeQuery.getTag()
            , graphVertexTatAttributeQuery.getTagName(), ConditionEnum.getCode(graphVertexTatAttributeQuery.getCondition()), graphVertexTatAttributeQuery.getTagValue());
        log.info("根据tag标签属性查询点 -gql语句:{}", vertexTagsQuery);
        return vertexTagsQuery;
    }


    /**
     * @return java.lang.String
     * @Description 创建索引
     * @Param [graphCreateIndex]
     **/
    public static String createIndex(GraphCreateIndex graphCreateIndex) {
        List<String> propertyList = CollectionUtil.newArrayList();
        for (AttributeBean attributeBean : graphCreateIndex.getAttributeBeanList()) {
            propertyList.add(attributeBean.getPropertyName());
        }
        String vertexTagsQuery = String.format("USE `%s`;CREATE %s INDEX `%s` on `%s`(%s) COMMENT '%s';"
            , graphCreateIndex.getSpace(), graphCreateIndex.getType(), graphCreateIndex.getIndexName(), graphCreateIndex.getTagEdgeName(),
            Joiner.on(",").join(propertyList), graphCreateIndex.getComment());
        log.info("创建索引 -gql语句:{}", vertexTagsQuery);
        return vertexTagsQuery;
    }

    /**
     * @return java.lang.String
     * @Description 属性详细信息
     * @Param [graphShowInfo]
     **/
    public static String showCreateAttributeInfo(GraphShowInfo graphShowInfo) {
        String showCreateAttributeInfo = String.format("USE `%s`; SHOW CREATE %s `%s`;"
            , graphShowInfo.getSpace(), graphShowInfo.getAttribute(), graphShowInfo.getAttributeName());
        log.info("查询属性的详细信息 -gql语句:{}", showCreateAttributeInfo);
        return showCreateAttributeInfo;
    }

    public static String showAttributePage(GraphPageAttribute graphPageAttribute) {

        int skip = (graphPageAttribute.getPageNum() - 1) * graphPageAttribute.getPageSize();
        if (AttributeEnum.TAGS.name().equalsIgnoreCase(graphPageAttribute.getAttribute())) {
            String showAttributePage = String.format("USE `%s`; MATCH (v) RETURN v SKIP %s LIMIT %s;"
                , graphPageAttribute.getSpace(), skip, graphPageAttribute.getPageSize());
            log.info("查询tag分页 -gql语句:{}", showAttributePage);
            return showAttributePage;
        }

        if (AttributeEnum.EDGES.name().equalsIgnoreCase(graphPageAttribute.getAttribute())) {
            String showAttributePage = String.format("USE `%s`;MATCH ()<-[e]-() RETURN e SKIP %s LIMIT %s;"
                , graphPageAttribute.getSpace(), skip, graphPageAttribute.getPageSize());
            log.info("查询edge分页 -gql语句:{}", showAttributePage);
            return showAttributePage;
        }
        return "";
    }

    public static <T> PageInfo<T> startPage(List<T> list, Integer pageNum, Integer pageSize) {
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
     * @return java.lang.String
     * @Description 修改点tag标签属性的值
     * @Param [graphUpdateVertex]
     **/
    public static String updateVertex(GraphUpdateVertex graphUpdateVertex) {

        List<String> tagList = graphUpdateVertex.getTagList();
        List<Object> tagValueList = graphUpdateVertex.getTagValueList();
        if (tagList.size() != tagValueList.size()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < tagList.size(); i++) {
            String tag = tagList.get(i);
            Object tagValue = tagValueList.get(i);
            stringBuffer.append("`").append(tag).append("`").append(" = ");
            if (tagValue instanceof String) {
                stringBuffer.append("'").append(tagValue).append("'");
            } else {
                stringBuffer.append(tagValue);
            }
            if (tagList.size() > 1 && (i + 1) != tagList.size()) {
                stringBuffer.append(",");
            }
        }

        String updateVertex = String.format("USE `%s`; UPDATE VERTEX ON `%s` '%s'  SET %s;"
            , graphUpdateVertex.getSpace(), graphUpdateVertex.getTagName(), graphUpdateVertex.getPointKey(), stringBuffer.toString());
        log.info("修改点 -gql语句:{}", updateVertex);
        return updateVertex;
    }

    /**
     * @return java.lang.String
     * @Description 分页查询创建的边
     * @Param [graphSpace]
     **/
    public static String edgePage(GraphSpace graphSpace) {
        String edgePage = String.format("USE `%s`;MATCH ()<-[e]-()RETURN e SKIP " + (graphSpace.getPageNum() - 1) +
            " LIMIT " + graphSpace.getPageSize() + " ;", graphSpace.getSpace());
        log.info("分页查询创建的边 -gql语句:{}", edgePage);
        return edgePage;
    }


    /**
     * @return java.lang.String
     * @Description 点的分页查询
     * @Param [graphSpace]
     **/
    public static String vertexPage(GraphSpace graphSpace) {
        String queryMatch = String.format("USE `%s`; match (v) return v skip " + (graphSpace.getPageNum() - 1) + " limit " + graphSpace.getPageSize() + ";", graphSpace.getSpace());
        log.info("点的分页查询 -gql语句:{}", queryMatch);
        return queryMatch;
    }

    /**
     * @Description 关系编辑
     * @Param [graphUpdateEdge]
     * @return java.lang.String
     **/
    public static String updateEdge(GraphUpdateEdge graphUpdateEdge) {
        List<String> edgeList = graphUpdateEdge.getEdgeList();
        List<Object> edgeValueList = graphUpdateEdge.getEdgeValueList();
        if (edgeList.size() != edgeValueList.size()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < edgeList.size(); i++) {
            String tag = edgeList.get(i);
            Object tagValue = edgeValueList.get(i);
            stringBuffer.append("`").append(tag).append("`").append(" = ");
            if (tagValue instanceof String) {
                stringBuffer.append("'").append(tagValue).append("'");
            } else {
                stringBuffer.append(tagValue);
            }
            if (edgeList.size() > 1 && (i + 1) != edgeList.size()) {
                stringBuffer.append(",");
            }
        }

        String updateVertex = String.format("USE `%s`; UPDATE EDGE ON `%s` '%s' -> '%s'@0 SET %s;"
            , graphUpdateEdge.getSpace(), graphUpdateEdge.getEdgeName(), graphUpdateEdge.getSrcVid(), graphUpdateEdge.getDstVid()
            , stringBuffer.toString());
        log.info("关系编辑 -gql语句:{}", updateVertex);
        return updateVertex;
    }
}
