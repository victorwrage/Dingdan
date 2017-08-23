package com.zdv.dingdan.bean;

import java.util.ArrayList;

/**
 * Info: 从星利源得到的客户信息
 * Created by xiaoyl
 * 创建时间:2017/4/17 14:36
 */

public class WDTResponseContent {
    String code;
    String ucode;
    String createtime;
    String totalprice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPayprice() {
        return payprice;
    }

    public void setPayprice(String payprice) {
        this.payprice = payprice;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoster() {
        return coster;
    }

    public void setCoster(String coster) {
        this.coster = coster;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getSolve() {
        return solve;
    }

    public void setSolve(String solve) {
        this.solve = solve;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getCrawl() {
        return crawl;
    }

    public void setCrawl(String crawl) {
        this.crawl = crawl;
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ArrayList<WDTResponseContentItem> getLists() {
        return lists;
    }

    public void setLists(ArrayList<WDTResponseContentItem> lists) {
        this.lists = lists;
    }

    String payprice;
    String paystate;
    String paytype;
    String status;
    String address;
    String coster;
    String company_id;
    String solve;
    String send_type;
    String crawl;
    String shoptype;
    String remark;
    ArrayList<WDTResponseContentItem> lists;

    @Override
    public String toString() {
        if(lists==null || lists.get(0)==null){
            return code+"--"+ucode+"--"+createtime+totalprice+payprice+paystate+paytype+status+address+coster+company_id+solve+send_type+crawl+shoptype+remark;
        }
        return code+"--"+ucode+"--"+createtime+totalprice+payprice+paystate+paytype+status+address+coster+company_id+solve+send_type+crawl+shoptype+remark+lists.get(0).toString();
    }
}
