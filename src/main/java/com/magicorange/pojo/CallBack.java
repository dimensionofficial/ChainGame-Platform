package com.magicorange.pojo;

import java.io.Serializable;

/**
 * @ClassNameCallBack
 * @Description
 * @Author
 * @Date2019/12/11 13:40
 **/
public class CallBack implements Serializable {
    private String callbackUrl;
    //amount
    private String realamount;//a
    private Integer appid;//a
    private String charid;//a
    private String cporderid;//a
    //传给接口 extinfo
    private String callbackinfo;//b
    //传给接口gold
    private Integer amount;//a
    private String  orderid;//a
    private  String serverid;//a
    private Integer uid;//a

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getRealamount() {
        return realamount;
    }

    public void setRealamount(String realamount) {
        this.realamount = realamount;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getCharid() {
        return charid;
    }

    public void setCharid(String charid) {
        this.charid = charid;
    }

    public String getCporderid() {
        return cporderid;
    }

    public void setCporderid(String cporderid) {
        this.cporderid = cporderid;
    }

    public String getCallbackinfo() {
        return callbackinfo;
    }

    public void setCallbackinfo(String callbackinfo) {
        this.callbackinfo = callbackinfo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "CallBack{" +
                "callbackUrl='" + callbackUrl + '\'' +
                ", realamount=" + realamount +
                ", appid=" + appid +
                ", charid='" + charid + '\'' +
                ", cporderid='" + cporderid + '\'' +
                ", callbackinfo='" + callbackinfo + '\'' +
                ", amount=" + amount +
                ", orderid='" + orderid + '\'' +
                ", serverid='" + serverid + '\'' +
                ", uid=" + uid +
                '}';
    }
}
