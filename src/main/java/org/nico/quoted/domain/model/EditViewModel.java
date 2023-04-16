package org.nico.quoted.domain.model;

import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.SourceInterface;
import org.slf4j.Logger;

// A helper class to store the source and quote to edit
// TODO Is there a more elegant way to do this?

public class EditViewModel {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EditViewModel.class);
    private static SourceInterface sourceToEdit;
    private static Quote quoteToEdit;

    public static SourceInterface getSourceToEdit() {
        return sourceToEdit;
    }

    public static void setSourceToEdit(SourceInterface sourceToEdit) {
        LOGGER.info("Setting source to edit: " + sourceToEdit.getTitle());
        EditViewModel.sourceToEdit = sourceToEdit;
    }

    public static Quote getQuoteToEdit() {
        return quoteToEdit;
    }

    public static void setQuoteToEdit(Quote quoteToEdit) {
        LOGGER.info("Setting quote to edit: " + quoteToEdit.getText());
        EditViewModel.quoteToEdit = quoteToEdit;
    }
}
