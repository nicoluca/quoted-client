package org.nico.quoted.api;

import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Source;

import java.util.List;

public class SourcesAPI implements CRUDInterface<Source> {

    private static SourcesAPI instance;
    private static List<Source> sources;

    public static SourcesAPI getInstance() {
        if (instance == null)
            instance = new SourcesAPI();
        return instance;
    }

    private SourcesAPI() {
        this.sources = BackendConstants.defaultSources();
    }

    @Override
    public Source create(Source source) {
        return null;
    }

    @Override
    public Source readById(long id) {
        return null;
    }

    @Override
    public List<Source> readAll() {
        return sources;
    }

    @Override
    public void update(Source source) {

    }

    @Override
    public void delete(Source source) {

    }

}
