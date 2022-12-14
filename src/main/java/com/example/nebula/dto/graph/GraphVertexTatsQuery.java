package com.example.nebula.dto.graph;


import com.example.nebula.dto.PageBeanDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("根据tag标签查询点入参实体")
public class GraphVertexTatsQuery extends PageBeanDto {

    @ApiModelProperty(value = "空间名称", example = "flceshi", required = true)
    private String space;

    //@ApiModelProperty(value = "标签集合", required = false)
    //private List<String> tagList;
    @ApiModelProperty(value = "标签", required = false)
    private String tag;

    @ApiModelProperty(value = "点id", required = false)
    private Object pointKey;
}
