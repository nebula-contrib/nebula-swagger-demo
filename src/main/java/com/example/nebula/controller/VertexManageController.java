package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphCreateVertex;
import com.example.nebula.dto.graph.GraphDeleteVertex;
import com.example.nebula.service.GraphCommonService;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.util.R;
import com.example.nebula.vo.CommonVo;
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
@Api(tags = "点编辑(实体)控制器")
@RequestMapping("/vertex")
public class VertexManageController {

    @Autowired
    GraphCommonService graphCommonService;

    @PostMapping("/createVertex")
    @ApiOperation("创建点(需要附加标签tag信息)")
    public R<List<CommonVo>> createVertex(@RequestBody GraphCreateVertex graphCreateVertex) {
        return R.data(graphCommonService.executeJson(NebulaUtil.createPoint(graphCreateVertex), CommonVo.class));
    }

    @PostMapping("/deleteVertex")
    @ApiOperation("删除点(根据点id删除)")
    public R<List<CommonVo>> deleteVertex(@RequestBody GraphDeleteVertex graphDeleteVertex) {
        return R.data(graphCommonService.executeJson(NebulaUtil.deleteVertex(graphDeleteVertex), CommonVo.class));
    }
}
