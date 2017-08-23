package com.zdv.dingdan.bean;

/**
 * Info: 从星利源得到的客户信息
 * Created by xiaoyl
 * 创建时间:2017/4/17 14:36
 */

public class WDTResponseCode {
    String errcode;
    String errmsg;
    WDTResponseContent content;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public WDTResponseContent getContent() {
        return content;
    }

    public void setContent(WDTResponseContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        if(content==null){
            return errcode+"--"+errmsg;
        }
        return errcode+"--"+errmsg+"--"+content.toString();
    }
}
