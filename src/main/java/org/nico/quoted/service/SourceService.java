package org.nico.quoted.service;

import org.nico.quoted.config.Config;
import org.nico.quoted.dao.ReadAllDao;
import org.nico.quoted.dao.ReadAll;
import org.nico.quoted.domain.Source;

import java.util.List;

public class SourceService implements ReadAll<Source> {
    private final ReadAllDao<Source> readAllDao = new ReadAllDao<>(Config.SOURCE_URL, Source.class);

    @Override
    public List<Source> readAll() {
        return readAllDao.readAll();
    }
}
