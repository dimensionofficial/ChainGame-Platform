package com.magicorange.myinterface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.magicorange")
@MapperScan("com.magicorange.dao")
@EnableTransactionManagement
public class MyinterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyinterfaceApplication.class, args);
    }

}
