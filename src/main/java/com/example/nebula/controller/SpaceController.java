package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphCreateSpace;
import com.example.nebula.dto.graph.GraphShowAttribute;
import com.example.nebula.dto.graph.GraphSpace;
import com.example.nebula.service.GraphCommonService;
import com.example.nebula.service.SpaceService;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.util.R;
import com.example.nebula.vo.CommonVo;
import com.example.nebula.vo.DetailSpace;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Descriptin: space控制器
 * @ClassName: SpaceController
 */
@RestController
@Api(tags = "图谱space控制器")
@RequestMapping("/space")
public class SpaceController {

    @Autowired
    GraphCommonService graphCommonService;

    @Autowired
    SpaceService spaceService;


    @PostMapping("/create")
    @ApiOperation("创建图谱")
    public R<List<CommonVo>> createSpace(@RequestBody GraphCreateSpace graphCreateSpace, @PathVariable String space) {
        return R.data(spaceService.createSpace(graphCreateSpace));
    }


    @PostMapping("/use")
    @ApiOperation("切换图谱")
    public R<List<CommonVo>> useSpace(@RequestBody GraphCreateSpace graphCreateSpace, @PathVariable String space) {
        return R.data(graphCommonService.executeJson(NebulaUtil.useSpace(graphCreateSpace.getSpace()), CommonVo.class));
    }

    @PostMapping("/list")
    @ApiOperation("卡片展示列表(图谱详情)")
    public R<List<DetailSpace>> detailSpace(@RequestBody GraphShowAttribute graphShowAttribute, @PathVariable String space) {
        return R.data(spaceService.detailSpace(graphShowAttribute));
    }

    @PostMapping("/info")
    @ApiOperation("查询某个空间的信息")
    public R spaceInfo(@RequestBody GraphSpace graphSpace, @PathVariable String space) {
        return R.data(spaceService.spaceInfo(graphSpace.getSpace()));
    }





}
