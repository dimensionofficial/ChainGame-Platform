package com.magicorange.util;

import com.magicorange.pojo.CallBack;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

/**
 * @ClassNameGetInterface
 * @Description
 * @Author
 * @Date2019/12/11 15:11
 **/
public class GetInterface {
    public static String restpost(CallBack callBack){
        System.out.println("请求参数:"+callBack.toString());
        //获取十位时间戳
        String time=String.valueOf(System.currentTimeMillis()).substring(0,10);
        //拼接签名
        String sign="amount="+callBack.getRealamount()+"&"+"appid="+callBack.getAppid()+"&"+"charid="+ UrlUtil.getURLDecoderString(callBack.getCharid())+"&"
                +"cporderid="+UrlUtil.getURLDecoderString(callBack.getCporderid())+"&"+"extinfo="+UrlUtil.getURLDecoderString(callBack.getCallbackinfo())+"&"+"gold="+callBack.getAmount()+"&"
                +"orderid="+callBack.getOrderid()+"&"+"serverid="+UrlUtil.getURLDecoderString(callBack.getServerid())+"&"+"time="+time+"&"
                +"uid="+callBack.getUid()+"+"+"fb33e4dc306e5466642af372dd207f31";
        System.out.println(sign);
        //MD5加密签名
        String MD5Sign=Md5Util.MD5(sign);
        System.out.println("加密后"+MD5Sign);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(callBack.getCallbackUrl())
                .queryParam("amount", callBack.getRealamount())
                .queryParam("appid", callBack.getAppid())
                .queryParam("charid", callBack.getCharid())
                .queryParam("cporderid", callBack.getCporderid())
                .queryParam("extinfo", callBack.getCallbackinfo())
                .queryParam("gold", callBack.getAmount())
                .queryParam("orderid", callBack.getOrderid())
                .queryParam("serverid",callBack.getServerid())
                .queryParam("time",time)
                .queryParam("uid",callBack.getUid())
                .queryParam("sign",MD5Sign);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);
        return response.getBody();
    }
}
