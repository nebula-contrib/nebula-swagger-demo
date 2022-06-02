package com.example.nebula.controller;


import com.example.nebula.dto.graph.GraphAddAttribute;
import com.example.nebula.dto.graph.GraphDelAttribute;
import com.example.nebula.dto.graph.GraphDropAttribute;
import com.example.nebula.service.AttributeService;
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

@RestController
@RequestMapping("/attribute")
@Api(tags = "属性编辑控制器")
public class AttributeManageController {

    @Autowired
    GraphCommonService graphCommonService;

    @Autowired
    AttributeService attributeService;

    @PostMapping("/dropAttribute")
    @ApiOperation("删除属性(删除 space空间 tag标签 edge边类型)")
    public R<List<CommonVo>> dropAttribute(@RequestBody GraphDropAttribute graphDropAttribute) {
        return R.data(attributeService.dropAttribute(graphDropAttribute));
    }

    @PostMapping("/addAttributeProperty")
    @ApiOperation("增加属性的子属性(tag标签的属性 edge边类型的属性)")
    public R<List<CommonVo>> addAttributeProperty(@RequestBody GraphAddAttribute graphAddAttribute) {
        return R.data(attributeService.addAttributeProperty(graphAddAttribute));
    }

    @PostMapping("/delAttributeProperty")
    @ApiOperation("删除属性的子属性(tag标签的属性 edge边类型的属性)")
    public R<List<CommonVo>> delAttributeProperty(@RequestBody GraphDelAttribute graphDelAttribute) {
        return R.data(graphCommonService.executeJson(NebulaUtil.delAttributeProperty(graphDelAttribute), CommonVo.class));
    }

}
