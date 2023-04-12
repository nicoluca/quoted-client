//package org.nico.quoted.api;
//
//import org.nico.quoted.config.LOGGER;
//import org.nico.quoted.domain.SourceInterface;
//import org.nico.quoted.ui.controller.BaseController;
//
//public class SourcesAPI implements CRUDInterface<SourceInterface> {
//
//    private static SourcesAPI instance;
//
//    public static SourcesAPI getInstance() {
//        if (instance == null)
//            instance = new SourcesAPI();
//        return instance;
//    }
//
//    @Override
//    public SourceInterface add(SourceInterface source) {
//        if (!BaseController.model.getSources().contains(source)) {
//            LOGGER.info("Source added: " + source);
//            BaseController.model.getSources().add(source);
//        } else
//            LOGGER.info("Source already exists: " + source);
//        return source;
//    }
//
//    @Override
//    public void delete(SourceInterface source) {
//
//    }
//
//    @Override
//    public void update(SourceInterface source) {
//
//    }
//
//}
