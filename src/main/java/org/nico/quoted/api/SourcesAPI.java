package org.nico.quoted.api;

import org.nico.quoted.config.Logger;
import org.nico.quoted.domain.SourceInterface;
import org.nico.quoted.ui.controller.BaseController;

public class SourcesAPI implements CRUDInterface<SourceInterface> {

    private static SourcesAPI instance;

    public static SourcesAPI getInstance() {
        if (instance == null)
            instance = new SourcesAPI();
        return instance;
    }

    @Override
    public SourceInterface add(SourceInterface source) {
        if (!BaseController.model.sources.contains(source)) {
            Logger.LOGGER.log(Logger.INFO, "Source added: " + source);
            BaseController.model.sources.add(source);
        } else
            Logger.LOGGER.log(Logger.INFO, "Source already exists: " + source);
        return source;
    }

    @Override
    public void delete(SourceInterface source) {

    }

    @Override
    public void update(SourceInterface source) {

    }

}
