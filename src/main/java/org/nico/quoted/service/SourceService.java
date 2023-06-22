package org.nico.quoted.service;

import org.nico.quoted.config.Config;
import org.nico.quoted.dao.Delete;
import org.nico.quoted.dao.DeleteDao;
import org.nico.quoted.dao.ReadAll;
import org.nico.quoted.dao.ReadAllDao;
import org.nico.quoted.domain.Source;

import java.util.List;

public class SourceService implements ReadAll<Source>, Delete<Source> {
    private final ReadAllDao<Source> readAllDao = new ReadAllDao<>(Source.class, Config.SOURCE_URL);

    private final DeleteDao<Source> deleteDao = new DeleteDao<>(Source.class, Config.SOURCE_URL);

    @Override
    public List<Source> readAll() {
        return readAllDao.readAll();
    }


    @Override
    public void delete(Source source) {
        deleteDao.delete(source);
    }
}
