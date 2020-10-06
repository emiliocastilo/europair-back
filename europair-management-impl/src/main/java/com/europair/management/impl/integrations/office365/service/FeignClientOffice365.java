package com.europair.management.impl.integrations.office365.service;

import com.google.gson.Gson;
import feign.Feign;
import feign.Headers;
import feign.Logger;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.Getter;

public interface FeignClientOffice365 {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void getUrlToEnabledFlightContributionInformation(String fileUri);


    /**
     * Static inner constructor class for feign clients
     */
    @Getter
    public static class Office365FeignClientBuilder {
        private String receivedUriToEnabledFlightContributionInformationClient = createFeignClientEnabledFlightContributionInformation(String.class, "http://localhost:8080/integration/office");

        private <T> T createFeignClientEnabledFlightContributionInformation(Class<T> type, String uri) {
            return Feign.builder()
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .logger(new Slf4jLogger(type))
                    .logLevel(Logger.Level.FULL)
                    .target(type, uri);
        }
    }

}
