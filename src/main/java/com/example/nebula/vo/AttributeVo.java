package com.example.nebula.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @Descriptin: 11
 * @ClassName: aaa
 */
@ApiModel("属性查询返回类")
public class AttributeVo {


    /**
     * spaceName : flceshi
     * latencyInUs : 1986
     * data : [{"meta":[""],"row":["basketballplayer"]},{"meta":[""],"row":["demo_lw"]},{"meta":[""],"row":["flceshi"]},{"meta":[""],"row":["lubrication_faultId_detection"]},{"meta":[""],"row":["lubrication_faultId_detection_sample"]},{"meta":[""],"row":["wentao_teatSpace"]},{"meta":[""],"row":["电影"]}]
     * columns : ["Name"]
     * errors : {"code":0}
     */

    @ApiModelProperty("空间名称")
    private String spaceName;
    @ApiModelProperty()
    private int latencyInUs;
    private ErrorsBean errors;
    @ApiModelProperty("查询返回属性集合")
    private List<DataBean> data;
    @ApiModelProperty("返回字段名集合")
    private List<String> columns;

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public int getLatencyInUs() {
        return latencyInUs;
    }

    public void setLatencyInUs(int latencyInUs) {
        this.latencyInUs = latencyInUs;
    }

    public ErrorsBean getErrors() {
        return errors;
    }

    public void setErrors(ErrorsBean errors) {
        this.errors = errors;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public static class ErrorsBean {
        /**
         * code : 0
         */
        @ApiModelProperty("错误码0正常")
        private int code;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class DataBean {
        @ApiModelProperty("元数据")
        private List<String> meta;
        @ApiModelProperty("字段值集合")
        private List<String> row;

        public List<String> getMeta() {
            return meta;
        }

        public void setMeta(List<String> meta) {
            this.meta = meta;
        }

        public List<String> getRow() {
            return row;
        }

        public void setRow(List<String> row) {
            this.row = row;
        }
    }

    private Map<String, List<String>> fieldMap;

    public Map<String, List<String>> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, List<String>> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
