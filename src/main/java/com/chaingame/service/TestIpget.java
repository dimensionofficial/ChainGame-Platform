package com.magicorange.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestIpget {
    @ResponseBody
    @RequestMapping(value = "/IPAndAddress",produces="application/json; charset=UTF-8")
    public String corsJsonSimple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(IPget.getIpAddress(request));
        return new IPget().doGet(request,response).toString();
    }

}