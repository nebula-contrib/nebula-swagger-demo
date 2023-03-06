package com.example.nebula.service;

import com.example.nebula.dto.ImportBean;
import com.example.nebula.entity.GraphFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * (GraphFile)表服务接口
 *
 * @author makejava
 * @since 2022-08-23 09:09:27
 */
public interface GraphFileService {

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 实例对象
     */
    GraphFile uploadFile(MultipartFile multipartFile);

    boolean importFile(ImportBean importBean) throws IOException;

    /**
     * 模板下载
     *
     * @return void
     * @Param [space]
     **/
    void template(String space, HttpServletResponse response);
}
