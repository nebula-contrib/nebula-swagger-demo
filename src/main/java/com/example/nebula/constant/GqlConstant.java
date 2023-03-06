package com.example.nebula.constant;

import com.google.common.base.Joiner;

/**
 * GQL 相关常量
 *
 * @author fulin by 2022/3/28
 */
public class GqlConstant {
    public static final String UNKNOWN = "unknown";
    public static final String ID = "id";
    public static final Joiner GQL_UNION_JOINER = Joiner.on(" UNION ");
    public static final Joiner GQL_UNION_COMMA = Joiner.on(",");

    private GqlConstant() {
    }

}
