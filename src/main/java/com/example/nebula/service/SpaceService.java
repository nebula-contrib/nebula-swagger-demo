package com.example.nebula.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.nebula.constant.AttributeEnum;
import com.example.nebula.dto.ImportDto;
import com.example.nebula.dto.graph.*;
import com.example.nebula.exception.GraphExecuteException;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.vo.AttributeVo;
import com.example.nebula.vo.CommonVo;
import com.example.nebula.vo.DetailSpace;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Descriptin: 图空间
 * @ClassName: SpaceService
 */
@Service
public class SpaceService {

    @Autowired
    GraphCommonService graphCommonService;


    public List<CommonVo> createSpace(GraphCreateSpace graphCreateSpace) {
        //GraphSpace bean = graphSpaceService.queryByChineseName(graphCreateSpace.getSpaceChineseName());
        //if (ObjectUtil.isNotNull(bean)) {
        //    throw new GraphExecuteException("图谱中文名重复");
        //}
        //GraphSpace graphSpace1 = graphSpaceService.queryByName(graphCreateSpace.getSpace());
        //if (ObjectUtil.isNotNull(graphSpace1)) {
        //    throw new GraphExecuteException("图谱标识重复");
        //}
        List list = graphCommonService.executeJson(NebulaUtil.createSpace(graphCreateSpace), CommonVo.class);
        //GraphSpace graphSpace = new GraphSpace();
        //graphSpace.setSpaceName(graphCreateSpace.getSpace());
        //graphSpace.setSpaceChineseName(graphCreateSpace.getSpaceChineseName());
        //graphSpace.setCreateTime(String.valueOf(DateUtil.date()));
        //graphSpace.setCreateUser(StpUtil.getLoginIdAsLong());
        //graphSpace.setVidType(graphCreateSpace.getFixedType() + graphCreateSpace.getSize());
        //graphSpace.setRemark(graphCreateSpace.getComment());
        //graphSpace.setReplicaFactor(graphCreateSpace.getReplicaFactor());
        //graphSpace.setPartitionNum(graphCreateSpace.getPartitionNum());
        //graphSpace.setTypeCode(graphCreateSpace.getTypeCode());
        //graphSpace.setClassifyCode(ClassifyCodeEnum.NEW.name());
        //graphSpaceService.insert(graphSpace);
        ////log.info("图空间mysql保存成功: {}", JSONUtil.toJsonPrettyStr(graphSpace));
        return list;
    }

    public List<DetailSpace> detailSpace(GraphShowAttribute graphShowAttribute) {

        // 所有图空间
        List<AttributeVo> spacesList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);
        AttributeVo attributeVo1 = spacesList.get(0);

        List<DetailSpace> detailSpaceList = CollectionUtil.newArrayList();

        DetailSpace detailSpace = null;
        for (AttributeVo.DataBean datum : attributeVo1.getData()) {
            int tagsNum = 0;
            int edgesNum = 0;
            int tag = 0;
            detailSpace = new DetailSpace();
            // 查询tgas/edges
            String spaceName = datum.getRow().get(0);
            graphShowAttribute.setSpace(spaceName);
            graphShowAttribute.setAttribute(AttributeEnum.TAGS.name());
            List<AttributeVo> tagsList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);

            AttributeVo attributeVoTag = tagsList.get(0);
            for (AttributeVo.DataBean attributeVoTagDatum : attributeVoTag.getData()) {
                tagsNum += attributeVoTagDatum.getRow().size();
            }
            graphShowAttribute.setAttribute(AttributeEnum.EDGES.name());
            List<AttributeVo> edgesList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);
            for (AttributeVo.DataBean dataBean : edgesList.get(0).getData()) {
                edgesNum += dataBean.getRow().size();
            }
            detailSpace.setSpace(spaceName);
            detailSpace.setTagsNum(tagsNum);
            detailSpace.setEdgesNum(edgesNum);
            detailSpaceList.add(detailSpace);
        }

        return detailSpaceList;
    }

    public List<AttributeVo> spaceInfo(String space) {
        return graphCommonService.executeJson(NebulaUtil.showAttributeInfo(GraphShowInfo.builder()
                .attribute("space").attributeName(space).space(space).build()), AttributeVo.class);
    }

    /**
     * csv数据导入
     *
     * @return boolean
     * @Param [request, file]
     **/

    @SneakyThrows
    public boolean importData(HttpServletRequest request, ImportDto importDto, MultipartFile file) {

        if (file.isEmpty()) {
            throw new GraphExecuteException("数据为空");
        }
        //String originalFilename = file.getOriginalFilename();
        //if (!originalFilename.endsWith(".csv")) {
        //    throw new GraphExecuteException("数据格式不对");
        //}

        //获取当前标签名称, 标签对应属性
        GraphShowInfo graphShowInfo = new GraphShowInfo();
        BeanUtil.copyProperties(importDto, graphShowInfo);
        List<AttributeVo> list = graphCommonService.executeJson(NebulaUtil.showAttributeInfo(graphShowInfo), AttributeVo.class);

        List<String> attributeList = CollectionUtil.newArrayList();
        for (AttributeVo.DataBean datum : list.get(0).getData()) {
            List<String> row = datum.getRow();
            if (CollectionUtil.isNotEmpty(row)) {
                String attributeName = row.get(0);
                attributeList.add(attributeName);
            }
        }

        // 开始识别数据

        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        List<LinkedHashMap> read = excelReader.read(0, 1, LinkedHashMap.class);
        Map map = read.get(0);
        List<String> arrayList = CollectionUtil.newArrayList(map.keySet());
        if (!CollUtil.containsAll(arrayList, attributeList)) {
            throw new GraphExecuteException("数据属性与对应实体属性对应错误");
        }

        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(read.size() / 500 + 1);

        // 开始插入
        String vidType = graphCommonService.getVidType(importDto.getSpace());

        if ("tag".equalsIgnoreCase(importDto.getAttribute())) {
            GraphCreateVertex graphCreateVertex = new GraphCreateVertex();
            graphCreateVertex.setSpace(importDto.getSpace());
            graphCreateVertex.setTagName(importDto.getAttributeName());
            graphCreateVertex.setTagList(attributeList);
            for (LinkedHashMap data : read) {
                executorService.execute(() -> {
                    graphCreateVertex.setPointKey(data.get(attributeList.get(0)));
                    graphCreateVertex.setTagValueList(new ArrayList<>(data.values()));
                    graphCommonService.executeJson(NebulaUtil.createPoint(graphCreateVertex, vidType), CommonVo.class);
                });
            }
        }

        ExecutorService executorServiceEdge = Executors.newFixedThreadPool(read.size() / 100 + 1);
        if ("edge".equalsIgnoreCase(importDto.getAttribute())) {
            GraphInsertEdge graphInsertEdge = new GraphInsertEdge();
            graphInsertEdge.setSpace(importDto.getSpace());
            graphInsertEdge.setEdgeName(importDto.getAttributeName());
            graphInsertEdge.setEdgeList(attributeList);
            for (LinkedHashMap data : read) {
                executorServiceEdge.execute(() -> {
                    List<String> keyList = new ArrayList<>(data.keySet());
                    graphInsertEdge.setSrcVid(data.get(keyList.get(0)).toString());
                    graphInsertEdge.setDstVid(data.get(keyList.get(1)).toString());

                    ArrayList edgeValueList = new ArrayList<>(data.values());
                    graphInsertEdge.setEdgeValueList(edgeValueList.subList(2, edgeValueList.size()));
                    graphCommonService.executeJson(NebulaUtil.insertEdge(graphInsertEdge, vidType), CommonVo.class);
                });
            }
        }

        return true;
    }

}
