package com.example.nebula.dto.graph;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("路径检索")
public class GraphVertexPathQuery {

    @ApiModelProperty(value = "空间名称", example = "movies", required = true)
    private String space;

    @ApiModelProperty(value = "路径查找类型: SHORTEST最短 | ALL所有 | NOLOOP非循环", example = "ALL", required = true)
    private String pathType;

    @ApiModelProperty(value = "变类型集合", required = true)
    private List<String> edgeList;

    @ApiModelProperty(value = "点的起始VID", required = true)
    private List<Object> srcVid;

    @ApiModelProperty(value = "点的目的VID", required = true)
    private List<Object> dstVid;

    @ApiModelProperty(value = "查询方向: REVERSELY反向 | BIDIRECT双向 | 空 正向", example = "BIDIRECT", required = true)
    private String direct;

    @ApiModelProperty(value = "最大步数", example = "3", required = true)
    private Integer step;

    @ApiModelProperty(value = "查询最大条数", required = true)
    private Integer resultSize;

    @ApiModelProperty(value = "筛选条件", required = true)
    private String condition;

    public String getEdgeList() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < edgeList.size(); i++) {
            String edge = edgeList.get(i);
            stringBuffer.append("`").append(edge).append("`");
            if (edgeList.size() > 1 && (i + 1) != edgeList.size()) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    public String getCondition() {
        if (StrUtil.isNotBlank(condition)) {
            return "WHERE " + condition;
        }
        return "";
    }
}
