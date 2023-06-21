package org.nico.quoted.dao;

import org.nico.quoted.domain.Identifiable;
import org.nico.quoted.http.HttpService;

public class DeleteDao<T extends Identifiable> extends Dao<T> implements Delete<T> {
    public DeleteDao(Class<T> type, String url) {
        super(type, url);
    }

    public DeleteDao(Class<T> type, String url, HttpService httpService) {
        super(type, url, httpService);
    }

    @Override
    public void delete(T t) {
        httpService.delete(url + "/" + t.getId());
    }
}
