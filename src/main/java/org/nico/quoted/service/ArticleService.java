package org.nico.quoted.service;

import org.nico.quoted.config.Config;
import org.nico.quoted.dao.Create;
import org.nico.quoted.dao.CreateDao;
import org.nico.quoted.dao.Update;
import org.nico.quoted.dao.UpdateDao;
import org.nico.quoted.domain.Article;

public class ArticleService implements Update<Article>, Create<Article> {

    private final UpdateDao<Article> updateDao = new UpdateDao<>(Article.class, Config.ARTICLES_URL);

    private final CreateDao<Article> createDao = new CreateDao<>(Article.class, Config.ARTICLES_URL);

    @Override
    public Article update(Article article) {
        return updateDao.update(article);
    }

    @Override
    public Article create(Article article) {
        return createDao.create(article);
    }
}
