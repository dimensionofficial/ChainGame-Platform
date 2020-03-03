package com.magicorange.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class DefuseToken {

    static String apiKey;
    static String defuseJWTurl;
    public  static String defuseToken;
    //defuseToken过期时间
    static String expires_at;

    public static void setToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> formEntity = new HttpEntity<String>("{\"api_key\":\"" + apiKey + "\"}", headers);
        //发送数据方法

        System.out.println(defuseJWTurl + apiKey);
        ResponseEntity<String> forEntity = restTemplate.postForEntity(defuseJWTurl, formEntity, String.class);
        //得到返回的数据body
        String jsonData = forEntity.getBody();
        JSONObject jsonObject = JSON.parseObject(jsonData);
        defuseToken = jsonObject.getString("token");
        expires_at = jsonObject.getString("expires_at");
    }

    public static String getDefuseToken() {
        if (null == expires_at || Long.valueOf(expires_at) < new Date().getTime()/1000) {
            setToken();
        }
        return defuseToken;
    }

    @Value("${apiKey}")
    public void setApiKey(String apiKey) {
        DefuseToken.apiKey = apiKey;
    }
    @Value("${defuseJWTurl}")
    public void setDefuseJWTurl(String defuseJWTurl) {
        DefuseToken.defuseJWTurl = defuseJWTurl;
    }
}
