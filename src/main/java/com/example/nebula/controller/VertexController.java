package com.example.nebula.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.example.nebula.constant.AttributeEnum;
import com.example.nebula.dto.NebulaJsonConverter;
import com.example.nebula.dto.NebulaVertexJsonResult;
import com.example.nebula.dto.graph.*;
import com.example.nebula.service.VertexService;
import com.example.nebula.util.R;
import com.example.nebula.vo.AttributeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Descriptin: 点控制器
 * @ClassName: VertexController
 */
@RestController
@Api(tags = "点查询(实体)控制器")
@RequestMapping("/vertex")
public class VertexController {

    @Autowired
    VertexService vertexService;

    @PostMapping("/vertexList")
    @ApiOperation("查询创建的点列表")
    public R<List<NebulaVertexJsonResult>> vertexList(@RequestBody GraphSpace graphSpace) {
        return R.data(vertexService.vertexList(graphSpace.getSpace()));
    }

    @PostMapping("/vertexTagsQuery")
    @ApiOperation("根据tag标签查询点")
    public R<List<NebulaVertexJsonResult>> vertexTagsQuery(@RequestBody GraphVertexTatsQuery graphVertexTatsQuery) {
        return R.data(vertexService.vertexTagsQuery(graphVertexTatsQuery));
    }

    @PostMapping("/vertexTagAttributeQuery")
    @ApiOperation("根据tag标签属性查询点(先要保证该标签属性已经建立索引)")
    public R<List<NebulaVertexJsonResult>> vertexTagAttributeQuery(@RequestBody GraphVertexTatAttributeQuery graphVertexTatAttributeQuery) {
        return R.data(vertexService.vertexTagAttributeQuery(graphVertexTatAttributeQuery));
    }

    @PostMapping("/expandQuery")
    @ApiOperation("根据点以及边信息扩展查询")
    public R<GraphData> vertexExpandQuery(@RequestBody GraphExpand graphExpand, @PathVariable String space) {
        List<NebulaVertexJsonResult> data = vertexService.vertexExpandQuery(graphExpand);
        com.alibaba.fastjson.JSONArray objects = com.alibaba.fastjson.JSON.parseArray(JSONUtil.toJsonStr(data));
        GraphData graphData = NebulaJsonConverter.toGraphDataMain(objects, new ArrayList<AttributeVo>());
        return R.data(graphData);
    }

    @PostMapping("/page")
    @ApiOperation("查询创建的点分页列表")
    public R vertexPage(@RequestBody GraphVertexTatsQuery graphVertexTatsQuery, @PathVariable String space) {
        return R.data(vertexService.vertexPage(graphVertexTatsQuery));
    }
}



