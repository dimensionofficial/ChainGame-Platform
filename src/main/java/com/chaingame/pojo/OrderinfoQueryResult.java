package com.magicorange.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassNameOrderinfoQueryResult
 * @Description
 * @Author
 * @Date2019/12/2 17:21
 **/
public class OrderinfoQueryResult implements Serializable {
    private String orderid;
    private String status;
    private String username;
    private String realamount;
    private String paytitle;
    private String dateline;
    private String paytype;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealamount() {
        return realamount;
    }

    public void setRealamount(String realamount) {
        this.realamount = realamount;
    }

    public String getPaytitle() {
        return paytitle;
    }

    public void setPaytitle(String paytitle) {
        this.paytitle = paytitle;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        String format = "yyyy-MM-dd HH:mm:ss";
        Long timeStamp = Long.parseLong(dateline)*1000L;
        String date = new SimpleDateFormat(format, Locale.CHINA).format(new Date(timeStamp));
        this.dateline = date;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    @Override
    public String toString() {
        return "OrderinfoQueryResult{" +
                "orderid='" + orderid + '\'' +
                ", status='" + status + '\'' +
                ", username='" + username + '\'' +
                ", realamount='" + realamount + '\'' +
                ", paytitle='" + paytitle + '\'' +
                ", dateline='" + dateline + '\'' +
                ", paytype='" + paytype + '\'' +
                '}';
    }
}
