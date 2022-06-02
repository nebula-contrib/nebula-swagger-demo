package com.example.nebula.controller;


import com.example.nebula.dto.NebulaVertexJsonResult;
import com.example.nebula.dto.graph.GraphSpace;
import com.example.nebula.dto.graph.GraphVertexTatAttributeQuery;
import com.example.nebula.dto.graph.GraphVertexTatsQuery;
import com.example.nebula.service.VertexService;
import com.example.nebula.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}



