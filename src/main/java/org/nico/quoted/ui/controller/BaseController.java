package org.nico.quoted.ui.controller;

import org.nico.quoted.model.ClientModel;
import org.nico.quoted.model.RepositoryModel;

public abstract class BaseController {
    protected static final ClientModel model = new ClientModel(new RepositoryModel());
}
