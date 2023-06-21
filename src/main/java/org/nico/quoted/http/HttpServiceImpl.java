package org.nico.quoted.http;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.nico.quoted.http.HttpService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class HttpServiceImpl implements HttpService {

    private final HttpClient httpClient;

    @Override
    public Optional<String> get(String url) {
        final HttpGet httpGet = new HttpGet(url);
        return execute(httpGet);
    }


    @Override
    public String post(String url, String payload) {
        final HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Accept", "application/json"); // If this is not set, no json is returned
        HttpEntity entity = new StringEntity(payload, "UTF-8");
        httpPost.setEntity(entity);

        Optional<String> result = execute(httpPost);

        if (result.isEmpty())
            throw new RuntimeException("Error while posting " + url + ": " + payload);

        return result.get();
    }

    @Override
    public String put(String url, String payload) {
        final HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setHeader("Accept", "application/json"); // If this is not set, no json is returned
        HttpEntity entity = new StringEntity(payload, "UTF-8");
        httpPut.setEntity(entity);

        Optional<String> result = execute(httpPut);

        if (result.isEmpty())
            throw new RuntimeException("Error while posting " + url + ": " + payload);

        return result.get();    }

    @Override
    public void delete(String url) {
        final HttpDelete httpDelete = new HttpDelete(url);
        execute(httpDelete);
    }

    private Optional<String> execute(HttpRequestBase request) {
        try (CloseableHttpResponse response =
                     (CloseableHttpResponse) httpClient.execute(request)) {

            if (response.getStatusLine().getStatusCode() == 404)
                return Optional.empty();
            else if (statusCodeNot2xx(response))
                throw new RuntimeException("Unknown error while retrieving " + request.getURI() + ": " + response.getStatusLine().getStatusCode());

            return evaluateResponse(response, request);

        } catch (IOException e) {
            log.warn("Error while retrieving " + request.getURI() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Optional<String> evaluateResponse(CloseableHttpResponse response, HttpRequestBase request) throws IOException {
        if (response.getEntity() == null) {
            log.info("No entity found for " + request.getURI());
            return Optional.empty();
        }

        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return Optional.of(responseString);
    }

    private static boolean statusCodeNot2xx(HttpResponse response) {
        return response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300;
    }
}
