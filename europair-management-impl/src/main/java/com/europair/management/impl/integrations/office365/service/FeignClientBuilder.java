package com.europair.management.impl.integrations.office365.service;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Factory builder for feign clients
 */
@Getter
@NoArgsConstructor
@Component
public class FeignClientBuilder {

    private FeignClientOffice365 feignClientOffice365Client = createFeignClient(FeignClientOffice365.class, "http://localhost:8080/integration/office");

    private <T> T createFeignClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, uri);
    }

}
