package org.nico.quoted.api;

import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.config.LOGGER;
import org.nico.quoted.domain.SourceInterface;

import java.util.List;

public class SourcesAPI implements CRUDInterface<SourceInterface> {

    private static SourcesAPI instance;
    private static List<SourceInterface> sources;

    public static SourcesAPI getInstance() {
        if (instance == null)
            instance = new SourcesAPI();
        return instance;
    }

    private SourcesAPI() {
        this.sources = BackendConstants.defaultSources();
    }

    @Override
    public SourceInterface create(SourceInterface sourceInterface) {
        return null;
    }

    @Override
    public SourceInterface readById(long id) {
        return null;
    }

    @Override
    public List<SourceInterface> readAll() {
        return sources;
    }

    @Override
    public void update(SourceInterface source) {

    }

    @Override
    public void delete(SourceInterface source) {

    }

}
