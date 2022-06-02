package com.example.nebula.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Descriptin: 通用返回类
 * @ClassName: CommonVo
 */
@ApiModel("操作通用返回类")
public class CommonVo {

    /**
     * spaceName : flceshi
     * latencyInUs : 2305
     * errors : {"code":0}
     */

    @ApiModelProperty("空间名称")
    private String spaceName;
    @ApiModelProperty()
    private int latencyInUs;
    private ErrorsBean errors;

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
}
