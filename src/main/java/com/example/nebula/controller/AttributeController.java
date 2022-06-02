package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphPageAttribute;
import com.example.nebula.dto.graph.GraphShowAttribute;
import com.example.nebula.dto.graph.GraphShowInfo;
import com.example.nebula.service.AttributeService;
import com.example.nebula.service.GraphCommonService;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.util.R;
import com.example.nebula.vo.AttributeVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attribute")
@Api(tags = "属性查询控制器")
public class AttributeController {

    @Autowired
    GraphCommonService graphCommonService;

    @Autowired
    AttributeService attributeService;

    @PostMapping("/showAttribute")
    @ApiOperation("属性查询(spaces tags edges列表)")
    public R<List<AttributeVo>> showAttribute(@RequestBody GraphShowAttribute graphShowAttribute) {
        return R.data(graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class));
    }


    @PostMapping("/pageListAttribute")
    @ApiOperation("属性分页查询 tags edges 分页列表")
    public R<PageInfo<AttributeVo.DataBean>> pageListAttribute(@RequestBody GraphPageAttribute graphPageAttribute) {
        return R.data(attributeService.pageListAttribute(graphPageAttribute));
    }


    @PostMapping("/showAttributeInfo")
    @ApiOperation("属性的子属性列表查询 tag edge 的属性列表查询")
    public R<List<AttributeVo>> showAttributeInfo(@RequestBody GraphShowInfo graphShowInfo) {
        return R.data(attributeService.showAttributeInfo(graphShowInfo));
    }

}
