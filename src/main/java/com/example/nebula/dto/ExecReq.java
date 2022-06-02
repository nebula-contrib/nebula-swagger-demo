package com.example.nebula.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 图语句执行
 *
 * @author fulin by 2022/3/19
 */
@Data
@Builder
public class ExecReq {
    private String space;
    private String gql;
    private List<String> paramList;

    public ExecReq setSpace(String space) {
        this.space = space;
        return this;
    }

    public String useSpaceAndGql() {
        return String.format("USE `%s`;%s", getSpace(), getGql());
    }

    public String getGql() {
        return HtmlUtils.htmlUnescape(gql);
    }
}
