package com.magicorange.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: nelson
 * @Description: get city by ip
 * @Date: created in 2018/03/31/17:40
 */
@Data
@AllArgsConstructor
public class IpVo implements Serializable{
    private String ip;
    private int port;
    private String host;
    private String region;
    private String city;
    private String isp;
}
