package org.nico.quoted.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.nico.quoted.config.Config;

import java.io.IOException;

public class HttpUtil {
    public static HttpResponse get(String url) throws IOException {
        final HttpGet httpGet = new HttpGet(url);
        return getHttpResponse(httpGet);
    }

    private static HttpResponse getHttpResponse(HttpGet httpGet) throws IOException{
        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(Config.getCredentialsProvider())
                .build();
             CloseableHttpResponse response = client.execute(httpGet))
        {
            return response;
        }
    }
}
