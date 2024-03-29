package com.example.nebula.dto.graph;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("扩展查询入参实体")
public class GraphExpand {

    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    @ApiModelProperty(value = "边类型集合", required = true)
    private List<String> edgeList;

    @ApiModelProperty(value = "方向: OUTFLOW(流出);INFLOW(流入);TWO-WAY(双向);", example = "TWO-WAY", required = true)
    private String direction;

    @ApiModelProperty(value = "步数开始(单步/范围的开始)", example = "1", required = true)
    private Integer stepStart;

    @ApiModelProperty(value = "步数结束(范围的结束;可无)", example = "2")
    @Min(1)
    private Integer stepEnd;

    @ApiModelProperty(value = "结果条数", example = "100", required = true)
    @Max(Integer.MAX_VALUE)
    @Min(1)
    private Integer resultSize = Integer.MAX_VALUE;

    @ApiModelProperty(value = "扩展点id集合", required = true)
    private List<Object> vidList;


    @ApiModelProperty(value = "条件(可为空)", example = " l.name CONTAINS '1' ",required = false)
    private String condition;

    public String getStepEndResult() {
        if (stepEnd != null) {
            return ".." + stepEnd;
        }
        return "";
    }

    public String getVidList(String vidType) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < vidList.size(); i++) {
            Object vid = vidList.get(i);

            if (vidType.contains("STRING")) {
                stringBuffer.append("\"").append(vid).append("\"");
            } else {
                stringBuffer.append(vid);
            }
            if (vidList.size() > 1 && (i + 1) != vidList.size()) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    //  l.degree CONTAINS 1 AND l.min_level == 2
    public String getCondition() {
        if (StrUtil.isNotBlank(condition)) {
            return " AND ALL(l IN e WHERE " + condition + ")";
        }
        return "";
    }
}
