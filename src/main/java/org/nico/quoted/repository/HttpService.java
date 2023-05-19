package org.nico.quoted.repository;

import java.util.Optional;

public interface HttpService {
    Optional<String> get(String endPoint);
    String post(String endPoint, String body);
    String put(String endPoint, String body);
    void delete(String endPoint);
}
