package org.nico.quoted.http;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

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
        log.info("Getting from " + url);
        return execute(httpGet);
    }


    @Override
    public String post(String url, String payload) {
        final HttpPost httpPost = new HttpPost(url);
        log.info("Posting to " + url + " with payload: " + payload);
        return execute(httpPost, payload);
    }

    @Override
    public String put(String url, String payload) {
        final HttpPut httpPut = new HttpPut(url);
        log.info("Putting to " + url + " with payload: " + payload);
        return execute(httpPut, payload);
    }

    private String execute(HttpEntityEnclosingRequestBase request, String payload) {
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json"); // If this is not set, no json is returned
        HttpEntity entity = new StringEntity(payload, "UTF-8");
        request.setEntity(entity);

        Optional<String> result = execute(request);

        if (result.isEmpty())
            throw new RuntimeException("Error while posting to " + request.getURI() + " with payload: " + payload);

        return result.get();
    }
    @Override
    public void delete(String url) {
        final HttpDelete httpDelete = new HttpDelete(url);
        log.info("Deleting from " + url);
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
