package com.magicorange.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefuseAPI {
    public  static String GETTRANSACTION;

    @Value("${GETTRANSACTION}")
    public void setGETTRANSACTION(String GETTRANSACTION) {
        DefuseAPI.GETTRANSACTION = GETTRANSACTION;
    }
}
