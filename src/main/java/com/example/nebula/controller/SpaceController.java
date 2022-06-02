package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphCreateSpace;
import com.example.nebula.dto.graph.GraphShowAttribute;
import com.example.nebula.service.GraphCommonService;
import com.example.nebula.service.SpaceService;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.util.R;
import com.example.nebula.vo.CommonVo;
import com.example.nebula.vo.DetailSpace;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/createSpace")
    @ApiOperation("创建图谱")
    public R<List<CommonVo>> createSpace(@RequestBody GraphCreateSpace graphCreateSpace) {
        return R.data(graphCommonService.executeJson(NebulaUtil.createSpace(graphCreateSpace), CommonVo.class));
    }


    @PostMapping("/useSpace")
    @ApiOperation("切换图谱")
    public R<List<CommonVo>> useSpace(@RequestBody GraphCreateSpace graphCreateSpace) {
        return R.data(graphCommonService.executeJson(NebulaUtil.useSpace(graphCreateSpace.getSpace()), CommonVo.class));
    }


    @PostMapping("/detailSpace")
    @ApiOperation("卡片展示(图谱详情)")
    public R<List<DetailSpace>> detailSpace(@RequestBody GraphShowAttribute graphShowAttribute) {
        return R.data(spaceService.detailSpace(graphShowAttribute));
    }


}
