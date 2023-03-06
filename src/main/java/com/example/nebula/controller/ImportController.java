package com.example.nebula.controller;

import com.example.nebula.dto.ImportDto;
import com.example.nebula.service.SpaceService;
import com.example.nebula.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Descriptin: space控制器
 * @ClassName: SpaceController
 */
@RestController
@Api(tags = "图谱数据导入控制器")
@RequestMapping("/import")
public class ImportController {

    @Autowired
    SpaceService spaceService;

    @PostMapping("/data")
    @ApiOperation("导入数据")
    public R importData(HttpServletRequest request,
                        @PathVariable String space,
                        ImportDto importDto,
                        @RequestParam("file") MultipartFile file) {
        return R.data(spaceService.importData(request, importDto,file));
    }



}
