package com.example.analytics.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;

@Configuration
public class JacksonConfig {

    @Bean
    public ProtobufModule protobufModule() {
        return new ProtobufModule();
    }



}
