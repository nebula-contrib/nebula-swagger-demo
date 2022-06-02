package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

/**
 * 搜索条件
 *
 * @author fulin by 2022/3/22
 */
@Data
public class SearchReq {
    /**
     * 搜索关键字
     */
    private String wd;
    /**
     * 指定标签
     */
    private List<String> tags;
}
