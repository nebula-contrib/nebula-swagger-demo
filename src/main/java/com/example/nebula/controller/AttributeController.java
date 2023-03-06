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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attribute")
@Api(tags = "属性查询控制器")
public class AttributeController {

    @Autowired
    GraphCommonService graphCommonService;

    @Autowired
    AttributeService attributeService;

    @PostMapping("/list")
    @ApiOperation("属性查询(spaces tags edges列表)")
    public R<List<AttributeVo>> showAttribute(@RequestBody GraphShowAttribute graphShowAttribute, @PathVariable String space) {
        return R.data(attributeService.showAttribute(graphShowAttribute));
    }


    @PostMapping("/page")
    @ApiOperation("属性分页查询 tags edges 分页列表")
    public R<PageInfo<AttributeVo.DataBean>> pageListAttribute(@RequestBody GraphPageAttribute graphPageAttribute, @PathVariable String space) {
        return R.data(attributeService.pageListAttribute(graphPageAttribute));
    }

    @PostMapping("/listProperty")
    @ApiOperation("属性的子属性列表查询 tag edge 的属性列表查询")
    public R<List<AttributeVo>> showAttributeInfo(@RequestBody GraphShowInfo graphShowInfo, @PathVariable String space) {
        return R.data(attributeService.showAttributeInfo(graphShowInfo));
    }

    @PostMapping("/propertyInfo")
    @ApiOperation("属性的详细信息")
    public R<List<AttributeVo>> showCreateAttributeInfo(@RequestBody GraphShowInfo graphShowInfo, @PathVariable String space) {
        return R.data(attributeService.showCreateAttributeInfo(graphShowInfo));
    }
}
