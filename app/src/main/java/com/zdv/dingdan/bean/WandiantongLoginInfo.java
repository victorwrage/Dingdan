package com.zdv.dingdan.bean;

/**
 * Info: 万店通登录
 * Created by xiaoyl
 * 创建时间:2017/4/22 14:36
 */

public class WandiantongLoginInfo {
    String errcode;
    String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    String last_login_time;
    String errmsg;
    UserInfo content;

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

    public UserInfo getContent() {
        return content;
    }

    public void setContent(UserInfo content) {
        this.content = content;
    }

    @Override
    public String toString() {
        if(content==null){
            return errcode+"|"+secret+"---"+errmsg;
        }
        return errcode+"|"+secret+"---"+errmsg+"|"+content.toString();
    }

    public class UserInfo{
        String code;
        String actual_name;
        String idcard;
        String sex;
        String company_name;
        String company_id;
        String imgurl;
        String last_login_ip;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getActual_name() {
            return actual_name;
        }

        public void setActual_name(String actual_name) {
            this.actual_name = actual_name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        @Override
        public String toString() {
            return  code+
             actual_name+
             idcard+
             sex+
             company_name+
             company_id+
             imgurl+
             last_login_ip;
        }
    }


}
