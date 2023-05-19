package org.nico.quoted.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.nico.quoted.config.DBConfig;

import java.io.IOException;

public class HttpUtil {
    public static HttpResponse get(String endpoint) throws IOException {
        final HttpGet httpGet = new HttpGet(DBConfig.SERVER_URL + endpoint);
        return getHttpResponse(httpGet);
    }

    private static HttpResponse getHttpResponse(HttpGet httpGet) throws IOException{
        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(DBConfig.getCredentialsProvider())
                .build();
             CloseableHttpResponse response = client.execute(httpGet))
        {
            return response;
        }
    }

    public static HttpResponse post(String endpoint, String json) throws IOException {
        final HttpPost httpPost = new HttpPost(DBConfig.SERVER_URL + endpoint);
        httpPost.setHeader("Content-type", "application/json");
        HttpEntity entity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(entity);

        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(DBConfig.getCredentialsProvider())
                .build();
             CloseableHttpResponse response = client.execute(httpPost))
        {
            return response;
        }
    }

    public static HttpResponse put(String url, String json) throws IOException {
        final HttpPut httpPut = new HttpPut(DBConfig.SERVER_URL + url);
        httpPut.setHeader("Content-type", "application/json");
        HttpEntity entity = new StringEntity(json, "UTF-8");
        httpPut.setEntity(entity);

        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(DBConfig.getCredentialsProvider())
                .build();
             CloseableHttpResponse response = client.execute(httpPut))
        {
            return response;
        }
    }

    public static HttpResponse delete(String url) throws IOException {
        final HttpDelete httpDelete = new HttpDelete(DBConfig.SERVER_URL + url);
        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(DBConfig.getCredentialsProvider())
                .build();
             CloseableHttpResponse response = client.execute(httpDelete))
        {
            return response;
        }
    }
}
