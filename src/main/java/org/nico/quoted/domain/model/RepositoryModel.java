package org.nico.quoted.domain.model;

import org.nico.quoted.repository.AuthorRepository;

public abstract class RepositoryModel {
    protected final AuthorRepository authorRepository = new AuthorRepository();
}
