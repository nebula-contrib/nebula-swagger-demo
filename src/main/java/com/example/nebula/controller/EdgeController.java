package com.example.nebula.controller;


import com.example.nebula.dto.NebulaVertexJsonResult;
import com.example.nebula.dto.graph.GraphSpace;
import com.example.nebula.service.GraphCommonService;
import com.example.nebula.util.NebulaUtil;
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
 * @Descriptin: 边控制器
 * @ClassName: VertexController
 */
@RestController
@Api(tags = "边edge查询控制器")
@RequestMapping("/edge")
public class EdgeController {

    @Autowired
    GraphCommonService graphCommonService;

    @PostMapping("/listEdge")
    @ApiOperation("查询插入的边edge(绑定关系查询)")
    public R<List<NebulaVertexJsonResult>> listEdge(@RequestBody GraphSpace graphSpace) {
        return R.data(graphCommonService.executeJson(NebulaUtil.listEdge(graphSpace), NebulaVertexJsonResult.class));
    }

}
