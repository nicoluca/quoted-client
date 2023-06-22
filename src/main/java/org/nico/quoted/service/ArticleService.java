package org.nico.quoted.service;

import org.nico.quoted.config.Config;
import org.nico.quoted.dao.Delete;
import org.nico.quoted.dao.DeleteDao;
import org.nico.quoted.dao.Update;
import org.nico.quoted.dao.UpdateDao;
import org.nico.quoted.domain.Article;

public class ArticleService implements Update<Article> {

    private final UpdateDao<Article> updateDao = new UpdateDao<>(Article.class, Config.ARTICLES_URL);


    @Override
    public Article update(Article article) {
        return updateDao.update(article);
    }
}
