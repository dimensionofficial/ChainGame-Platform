package com.magicorange.controller;

import com.alibaba.fastjson.JSONObject;
import com.magicorange.dao.IndexDao;
import com.magicorange.pojo.OrderinfoQueryResult;
import com.magicorange.pojo.TransferData;
import com.magicorange.service.IndexService;
import com.magicorange.util.BaseResult;
import com.magicorange.util.GetInterface;
import com.magicorange.util.RestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassNameIndexController
 * @Description
 * @Author
 * @Date2019/11/28 18:52
 **/
@Controller
public class IndexController{

    String cporderid="";
    String fromAcc="";
    @Resource
    private IndexService indexService;

    @Resource
    private IndexDao indexDao;

    @RequestMapping("inputpayinfo")
    @ResponseBody
    public String insert(String uid,@RequestParam(required = false) String token, String username,@RequestParam(required = false) String appid,@RequestParam(required = false) String serverid,@RequestParam(required = false) String amount,
                         String realamount, @RequestParam(required = false)String remark, String payfrom, String paytitle,@RequestParam(required = false) String ip,
                         @RequestParam(required = false)String location,@RequestParam(required = false)  String dateline,@RequestParam(required = false)String callbackinfo, String cporderid,@RequestParam(required = false)String charid, String paytype,
                         @RequestParam(required = false)String paymoney, @RequestParam(required = false)String accountbalance, @RequestParam(required = false)String daibi,@RequestParam(required = false)String touid,@RequestParam(required = false)String tousername,
                         @RequestParam(required = false)String ostype, @RequestParam(required = false)String discount, @RequestParam(required = false)String iospaytest, @RequestParam(required = false,value = "from") String fromAcc,
                         @RequestParam("to") String toAcc, String symbol,@RequestParam(required = false) String contractName,
                         @RequestParam("decimal") String decimals,@RequestParam(required = false) String memo, @RequestParam(required = false)String info,@RequestParam(required = false) String callbackUrl){
        System.out.println("进入到提交订单界面===========================================");
        System.out.println("uid:"+uid+" username:"+username+" realamount:"+realamount+" payfrom:"+payfrom+" paytitle:"+paytitle+" cporderid:"+cporderid
                +" paytype:"+paytype+" fromAcc:"+fromAcc+" toAcc:"+toAcc+" symbol:"+symbol+" decimals:"+decimals);
        //把必填字段加入list进行判断
        List<String> notNullList=Arrays.asList(uid,username,realamount,payfrom,paytitle,cporderid,paytype,toAcc,symbol,decimals);
        //判断收款账户是否为正确
        if (true!= "mteoshowlove".equals(toAcc)){
            return "304";
        }else if (indexService.notNull(notNullList)==0){
            return "305";
        }
        System.out.println("必填字段都已填");
        //获取时间拼接成orderid
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(d)+d.getTime();
        String orderid=time.substring(0,18);
        System.out.println("orderid已生成");
        //OrderInfo表需要的数据
        List<String > insertOrderInfo=Arrays.asList(orderid,uid,username,appid,serverid,amount,realamount,remark,payfrom,paytitle,ip,location
        ,dateline,callbackinfo,cporderid,charid,paytype,paymoney,accountbalance,daibi,touid,tousername,ostype,discount,iospaytest);
        System.out.println("orderInfo表已生成");
        //eostransferinfo表需要的数据
        List<String > insertEostransferinfo =Arrays.asList(orderid,fromAcc,toAcc,amount,symbol,contractName,decimals,memo,info,callbackUrl);
        System.out.println("eostransferinfo表已生成");

        insertOrderInfo=indexService.autoMaticZero(insertOrderInfo);
        System.out.println("未填字段填充为0成功");
        insertEostransferinfo=indexService.autoMaticZero(insertEostransferinfo);
        System.out.println("未填字段填充为0成功");
        /**
         * 参数呈现
         */
        System.out.println("接收字段打印");
        notNullList.forEach(k->{
            System.out.println(k);
        });
        insertOrderInfo.forEach(k->{
            System.out.println(k);
        });
        insertEostransferinfo.forEach(k->{
            System.out.println(k);
        });
        //调用Service层里插入方法返回结果 1:成功
        if (indexService.insert(insertOrderInfo,insertEostransferinfo)==2){
            return "200";
        }else {
            return "300";
        }
    }
    @RequestMapping("inputpayresult")
    @ResponseBody
    public Map update(@RequestBody JSONObject jsonParam) throws InterruptedException {
        System.out.println("进入到支付界面======================================");
        Map<String,String> map=new HashMap();
        String tx_id= (String) jsonParam.get("tx_id");
        System.out.println("tx_id为"+tx_id);
        if (tx_id==null||"".equals(tx_id)){
            map.put("code","2");
            map.put("message","tx_id不能为空");
            return map;
        }
        //通过前台传的tx_id获取orderid
        TransferData transferData= RestUtil.getTransferData(tx_id);
        System.out.println("接口调用结果"+transferData);
        int count=0;
        while (null==transferData){
            count=count+1;
            Thread.sleep(500);
            transferData=RestUtil.getTransferData(tx_id);
            System.out.println("第"+count+"次查询");
            System.out.println(transferData);
            if (transferData!=null){
                break;
            }
            if (count==20){
                System.out.println("查询了20次 失败返回");
                map.put("code","2");
                map.put("message","没有查到该订单号");
                return map;
            }
        }
        cporderid=transferData.getMemo();
        fromAcc=transferData.getFrom();
        System.out.println("前台传的"+tx_id);
        System.out.println("通过Tx_id获取到的cpOrderid"+transferData.getMemo());
        Integer updateResultCount=indexService.update(cporderid,tx_id,fromAcc);
        System.out.println("支付结果:2=成功::");
        if (updateResultCount==2){
            map.put("sign","1");
            map.put("txId",tx_id);
            //开启多线程
//            IndexController indexController=new IndexController();
//            indexController.start();
            String status=GetInterface.restpost(indexService.callBack(cporderid));
            System.out.println("接口回调结果"+status);
            if ("SUCCESS".equals(status)){
                System.out.println("接口返回success后修改通知结果:"+indexDao.updateCallBack(cporderid));
            }
            return map;
        }else{
            map.put("code","2");

            map.put("message","支付失败");
            return map;
        }
    }
    @RequestMapping("orderinfoqueryresult")
    @ResponseBody
    public BaseResult orderinfoqueryresult(String cporderid){
        OrderinfoQueryResult orderinfoQueryResult=indexService.orderinfoqueryresult(cporderid);
        System.out.println(orderinfoQueryResult);
        if (orderinfoQueryResult==null){
            return BaseResult.error(300, "查无信息,请输入正确的cporderid");
        }
        return BaseResult.success(200, indexService.orderinfoqueryresult(cporderid));
    }

//    @Override
//    public void run() {
//        System.out.println("回调接口开始");
//        System.out.println(GetInterface.restpost(indexService.callBack(cporderid)));
//    }
    @RequestMapping("Test")
    @ResponseBody
    public TransferData Test(String tx_id) throws InterruptedException {
        TransferData transferData= RestUtil.getTransferData(tx_id);
        System.out.println("接口调用结果"+transferData);
        int count=0;
        while (null==transferData){
            count=count+1;
            Thread.sleep(300);
            transferData=RestUtil.getTransferData(tx_id);
            System.out.println("第"+count+"次查询");
            System.out.println(transferData);
            if (transferData!=null){
                break;
            }
            if (count==100){
                return null;
            }
        }
        return transferData;
    }
}
