package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphCreateIndex;
import com.example.nebula.dto.graph.GraphCreateTagEdge;
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
 * @Descriptin: 点类型标签tag控制器
 * @ClassName: TagController
 */
@RestController
@Api(tags = "标签tag edge 新增控制器")
@RequestMapping("/tagEdge")
public class TagEdgeController {

    @Autowired
    GraphCommonService graphCommonService;

    @PostMapping("/createTagEdge")
    @ApiOperation("创建tag 或者 edge")
    public R<List<CommonVo>> createTagEdge(@RequestBody GraphCreateTagEdge graphCreateTagEdge) {
        return R.data(graphCommonService.executeJson(NebulaUtil.createTagEdge(graphCreateTagEdge), CommonVo.class));
    }


    @PostMapping("/createIndex")
    public R<List<CommonVo>> createIndex(@RequestBody GraphCreateIndex graphCreateIndex) {
        return R.data(graphCommonService.executeJson(NebulaUtil.createIndex(graphCreateIndex), CommonVo.class));
    }
}
