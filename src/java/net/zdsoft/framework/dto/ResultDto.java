package net.zdsoft.framework.dto;

import com.wanpeng.basedata.remote.dto.BaseDto;

public class ResultDto extends BaseDto {
    private boolean success;
    private String code;
    private String msg;
    
    public boolean isSuccess() {
        return success;
    }

    public ResultDto setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResultDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
