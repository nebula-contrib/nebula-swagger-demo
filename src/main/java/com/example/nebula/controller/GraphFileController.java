package com.example.nebula.controller;

import com.example.nebula.dto.ImportBean;
import com.example.nebula.entity.GraphFile;
import com.example.nebula.service.GraphFileService;
import com.example.nebula.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * (GraphFile)表控制层
 *
 * @author makejava
 * @since 2022-08-23 09:09:26
 */
@RestController
@Api(tags = "文件控制器")
@RequestMapping("graphFile")
public class GraphFileController {


    @Resource
    private GraphFileService graphFileService;

    /**
     * 文件上传
     * @param file 文件
     * @return 文件实例对象
     */

    @ApiOperation("文件上传--可以不做,这里为了预览数据")
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<GraphFile> uploadFile(@RequestParam("file") MultipartFile file) {
        return R.data(this.graphFileService.uploadFile(file));
    }

    @ApiOperation("文件导入--执行nebula import插件")
    @PostMapping("/import")
    public R<Boolean> importFile(@RequestBody ImportBean importBean) throws IOException {
        return R.data(this.graphFileService.importFile(importBean));
    }

    @ApiOperation("模板下载,可以填充数据")
    @GetMapping("/template")
    public void template(@RequestParam String space, HttpServletResponse response){
        graphFileService.template(space,response);
    }

}

