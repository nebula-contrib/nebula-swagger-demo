package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphDeleteEdge;
import com.example.nebula.dto.graph.GraphInsertEdge;
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
 * @Descriptin: 边控制器
 * @ClassName: VertexController
 */
@RestController
@Api(tags = "边edge管理控制器")
@RequestMapping("/edge")
public class EdgeManageController {

    @Autowired
    GraphCommonService graphCommonService;

    @PostMapping("/insertEdge")
    @ApiOperation("插入边edge(关系编辑-绑定两个点的关系)")
    public R<List<CommonVo>> insertEdge(@RequestBody GraphInsertEdge graphInsertEdge) {
        return R.data(graphCommonService.executeJson(NebulaUtil.insertEdge(graphInsertEdge), CommonVo.class));
    }

    @PostMapping("/deleteEdge")
    @ApiOperation("删除边edge(解除关系编辑-解除两个点的关系)")
    public R<List<CommonVo>> deleteEdge(@RequestBody GraphDeleteEdge graphDeleteEdge) {
        return R.data(graphCommonService.executeJson(NebulaUtil.deleteEdge(graphDeleteEdge), CommonVo.class));
    }


}
