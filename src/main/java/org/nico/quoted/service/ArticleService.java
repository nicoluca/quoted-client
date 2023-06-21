package org.nico.quoted.service;

import org.nico.quoted.config.Config;
import org.nico.quoted.dao.Delete;
import org.nico.quoted.dao.DeleteDao;
import org.nico.quoted.dao.Update;
import org.nico.quoted.dao.UpdateDao;
import org.nico.quoted.domain.Article;

public class ArticleService implements Update<Article>, Delete<Article> {

    private final UpdateDao<Article> updateDao = new UpdateDao<>(Article.class, Config.ARTICLES_URL);
    private final DeleteDao<Article> deleteDao = new DeleteDao<>(Article.class, Config.ARTICLES_URL);

    @Override
    public void delete(Article article) {
        deleteDao.delete(article);
    }

    @Override
    public Article update(Article article) {
        return updateDao.update(article);
    }
}
