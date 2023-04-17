package org.nico.quoted.repository;

import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Source;

import java.util.List;
import java.util.Optional;

public class SourcesAPI implements CRUDRepository<Source> {

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
    public void create(Source source) {
    }

    @Override
    public Optional<Source> readById(long id) {
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
