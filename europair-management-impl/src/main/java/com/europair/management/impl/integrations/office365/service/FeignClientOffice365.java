package com.europair.management.impl.integrations.office365.service;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface FeignClientOffice365 {

    @RequestLine("GET ?fileUri={fileUri}")
    @Headers("Content-Type: application/json")
    void sendUriToEnabledFlightContributionInformation(@Param("fileUri")String fileUri);


}
