package com.example.nebula.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
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

/**
 * @Descriptin: 属性操作
 * @ClassName: AttributeService
 */
@Service
public class AttributeService {


    @Autowired
    GraphCommonService graphCommonService;

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
     * @Description 属性的子属性查询
     * @Param [graphShowInfo]
     * @return java.util.List<com.hoteamsoft.common.vo.AttributeVo>
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
        stringListHashMap.put(key,valueList);
        attributeVo.setFieldMap(stringListHashMap);
    }
}
