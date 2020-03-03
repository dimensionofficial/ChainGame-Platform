package com.magicorange.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.magicorange.pojo.TransferData;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RestUtil {

    public static String restpost(String url, String jsonString) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //设置类型
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        //为了调用eos接口而加两个头
        headers.add("apikey","5cf38fdc-567f-452e-9f3d-d550f0dfab43");
        headers.add("accept","application/json;charset=UTF-8");
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonString, headers);
        //发送数据方法
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url, formEntity, String.class);
        //得到返回的数据body
        String jsonData = forEntity.getBody();
        return jsonData;
    }

    //defuse
    public static String restGetDefuse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestheaders = new HttpHeaders();
        requestheaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestheaders.setBearerAuth(DefuseToken.getDefuseToken());
//        String result=restTemplate.getForObject("https://mainnet.eos.dfuse.io/v0/transactions/a80067ffa03dea2cb772b0c64f19d9c58e3a7c3d4bd6c3f063a2546c58e39c76",String.class);
        HttpEntity<String> formEntity = new HttpEntity<String>(requestheaders);
//        ResponseEntity<String> result = restTemplate.exchange("https://mainnet.eos.dfuse.io/v0/transactions/a80067ffa03dea2cb772b0c64f19d9c58e3a7c3d4bd6c3f063a2546c58e39c76", HttpMethod.GET, formEntity, String.class);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, formEntity, String.class);
        String body = responseEntity.getBody();
        return body;
    }

    //map转json串
    public static String getJsonString(Map<String, String> map) {
        if (null == map || null == map.entrySet()) {
            return "";
        }
        Iterator it = map.entrySet().iterator();
        if (null == it) return "";
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) it.next();
            sb.append("\"");
            sb.append(entry.getKey());
            sb.append("\":\"");
            sb.append(entry.getValue());
            sb.append("\"");
            if (it.hasNext()) sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
//        TransferData data = restTem.getTransferData("aa91030215e846044b4ebe5246801ed33dd4aaf6da49c4e178d81f3720eb967a");
        TransferData data = RestUtil.getTransferData("21647a1b3f7b424b61428e0e35fec641a6e21c16c400fa47e5fe912a1af57a79");
        System.out.println(data);
    }

    public static TransferData getTransferData(String tx_id) {
        Map map = new HashMap();
        map.put("id",tx_id);
        String jsonstr = getJsonString(map);
        String restResult;
        try {
            System.out.println("进入到Try");
            restResult = RestUtil.restpost("https://open-api.eos.blockdog.com/v2/third/get_transaction",jsonstr);
            JSONObject jsonObject = JSON.parseObject(restResult);
            JSONArray array = jsonObject.getJSONArray("action_traces");
            JSONObject transaction = array.getJSONObject(array.size()-1);
            JSONObject act = transaction.getJSONObject("act");
            return act.getObject("data", TransferData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static TransferData getDefuseTransferData(String url) {
        String restResult;
        try {
            System.out.println("进入到defuse的api");
            restResult = RestUtil.restGetDefuse(url);
            //下边都是为了取值
            JSONObject jsonObject = JSON.parseObject(restResult);
            JSONObject execution_trace = jsonObject.getJSONObject("execution_trace");
            JSONArray array = execution_trace.getJSONArray("action_traces");
            JSONObject action_trace1 = array.getJSONObject(0);
            JSONArray inline_traces = action_trace1.getJSONArray("inline_traces");
            JSONObject inline_trace = inline_traces.getJSONObject(inline_traces.size()-1);
            JSONArray innerinline_traces = inline_trace.getJSONArray("inline_traces");
            JSONObject transaction = innerinline_traces.getJSONObject(innerinline_traces.size()-1);
            JSONObject act = transaction.getJSONObject("act");
            TransferData transferData = act.getObject("data", TransferData.class);
            transferData.setTransaction_status(jsonObject.getString("transaction_status"));
            transferData.setExecution_irreversible(jsonObject.getString("execution_irreversible"));
            return transferData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
