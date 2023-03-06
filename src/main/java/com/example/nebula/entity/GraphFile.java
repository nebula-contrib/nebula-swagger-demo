package com.example.nebula.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (GraphFile)实体类
 *
 * @author makejava
 * @since 2022-08-23 09:09:27
 */
public class GraphFile implements Serializable {
    private static final long serialVersionUID = -70939095818612018L;
    /**
     * 文件id
     */
    private Integer id;
    /**
     * 上传用户id
     */
    private Integer userId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 保留2
     */
    private String extend;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

}

