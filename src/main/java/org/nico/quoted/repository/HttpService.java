package org.nico.quoted.repository;

public interface HttpService {
    String get(String endPoint);
    String post(String endPoint, String body);
    String put(String endPoint, String body);
    void delete(String endPoint);
}
