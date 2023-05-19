package org.nico.quoted.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Slf4j
public class HttpServiceImpl implements HttpService {

    private final HttpClient httpClient;

    @Override
    public String get(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if (statusCodeNot2xx(response))
                throw new RuntimeException("Error while retrieving " + url + ": " + response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, StandardCharsets.UTF_8);

        } catch (IOException e) {
            log.warn("Error while retrieving " + url + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public String post(String url, String payload) {
        final HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        HttpEntity entity = new StringEntity(payload, "UTF-8");
        httpPost.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            if (statusCodeNot2xx(response))
                throw new RuntimeException("Error while posting to  " + url + " with payload '" + payload + "': " + response.getStatusLine().getStatusCode());

            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("Error while posting to " + url + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String put(String endPoint, String body) {
        return null; // TODO
    }

    @Override
    public void delete(String endPoint) {
        // TODO
    }

    private static boolean statusCodeNot2xx(HttpResponse response) {
        return response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300;
    }
}
