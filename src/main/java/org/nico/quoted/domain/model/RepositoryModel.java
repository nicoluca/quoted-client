package org.nico.quoted.domain.model;

import org.nico.quoted.api.AuthorRepository;

public abstract class RepositoryModel {
    protected final AuthorRepository authorRepository = new AuthorRepository();
}
