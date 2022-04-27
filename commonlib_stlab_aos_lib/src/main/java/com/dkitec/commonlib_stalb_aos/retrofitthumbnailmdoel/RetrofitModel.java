package com.dkitec.commonlib_stalb_aos.retrofitthumbnailmdoel;

import java.io.Serializable;

public class RetrofitModel implements Serializable {
    private String code;
    private String errMsg;
    private Data data;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }
}
