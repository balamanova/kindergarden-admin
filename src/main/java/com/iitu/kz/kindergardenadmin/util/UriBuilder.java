package com.iitu.kz.kindergardenadmin.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UriBuilder {

    private static String ROLE_API = "https://api.graph.cool/simple/v1/ck0vgudit0bzk0126ciuladiv";

    public static URI roleUriBuilder(String query) {
        return UriComponentsBuilder
                .fromUriString(ROLE_API)
                .queryParam("query", query)
                .build()
                .toUri();
    }

    private static HttpEntity httpEntityConstructor(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.ALL));
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("query", query);
        return new HttpEntity(stringMap, headers);
    }
}

