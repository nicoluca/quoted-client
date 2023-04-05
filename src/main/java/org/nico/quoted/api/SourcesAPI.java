package org.nico.quoted.api;

import org.nico.quoted.config.Logger;
import org.nico.quoted.domain.Quotable;
import org.nico.quoted.ui.controller.BaseController;

public class SourcesAPI implements CRUDInterface<Quotable> {

    private static SourcesAPI instance;

    public static SourcesAPI getInstance() {
        if (instance == null)
            instance = new SourcesAPI();
        return instance;
    }

    @Override
    public Quotable add(Quotable source) {
        if (!BaseController.model.sources.contains(source)) {
            Logger.LOGGER.log(Logger.INFO, "Source added: " + source);
            BaseController.model.sources.add(source);
        } else
            Logger.LOGGER.log(Logger.INFO, "Source already exists: " + source);
        return source;
    }

    @Override
    public void delete(Quotable source) {

    }

    @Override
    public void update(Quotable source) {

    }

}
