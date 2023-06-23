package org.nico.quoted.dao;

import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Identifiable;
import org.nico.quoted.http.HttpService;

@Slf4j
public class DeleteDao<T extends Identifiable> extends Dao<T> implements Delete<T> {
    public DeleteDao(Class<T> type, String url) {
        super(type, url);
    }

    // Used for unit testing, to inject a mock HttpService
    public DeleteDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    @Override
    public void delete(T t) {
        log.info("Deleting {} with id {} from {}", t, t.getId(), url);
        httpService.delete(url + "/" + t.getId());
    }
}
