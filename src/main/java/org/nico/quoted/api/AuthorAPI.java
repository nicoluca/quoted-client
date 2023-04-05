package org.nico.quoted.api;

import org.nico.quoted.config.Logger;
import org.nico.quoted.domain.Author;
import org.nico.quoted.ui.controller.BaseController;

public class AuthorAPI implements CRUDInterface<Author> {
    private static AuthorAPI instance;

    public static AuthorAPI getInstance() {
        if (instance == null)
            instance = new AuthorAPI();
        return instance;
    }

    public Author getAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        if (!BaseController.model.authors.contains(author)) {
            Logger.LOGGER.log(Logger.INFO, "Author added: " + author);
            BaseController.model.authors.add(author);
        } else
            Logger.LOGGER.log(Logger.INFO, "Author already exists: " + author);
        return author;
    }

    @Override
    public Author add(Author author) {
        return null;
    }

    @Override
    public void delete(Author author) {

    }

    @Override
    public void update(Author author) {

    }
}