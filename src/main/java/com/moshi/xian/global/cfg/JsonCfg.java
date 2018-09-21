package com.moshi.xian.global.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @Author: Hogan [QQ:82425753]
 * @Tip:
 * @Modify Histories
 * -----------------------------------------
 * @1: 2018/1/19 09:12 : Created by Hogan
 */
@Configuration
public class JsonCfg {
    @Bean
    public ObjectMapper ObjectMapper(){
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,true);
        return objectMapper;
    }
}
