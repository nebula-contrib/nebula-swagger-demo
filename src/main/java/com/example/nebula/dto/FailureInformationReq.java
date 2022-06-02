package com.example.nebula.dto;

import lombok.Data;

import java.util.List;

/**
 * 失效信息
 *
 * @author fulin by 2022/3/22
 */
@Data
public class FailureInformationReq {
    /**
     * 故障现象 id 值
     */
    private List<String> tagFailurePhenomenonIds;
}
