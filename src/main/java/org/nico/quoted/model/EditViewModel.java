package org.nico.quoted.model;

import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.Source;

// A helper class to store the source and quote to edit

@Slf4j
public final class EditViewModel {
    private static Source sourceToEdit;
    private static Quote quoteToEdit;

    public static Source getSourceToEdit() {
        return sourceToEdit;
    }

    public static void setSourceToEdit(Source sourceToEdit) {
        if (sourceToEdit == null)
            log.info("Setting source to edit: null");
        else
            log.info("Setting source to edit: " + sourceToEdit.getTitle());
        EditViewModel.sourceToEdit = sourceToEdit;
    }

    public static Quote getQuoteToEdit() {
        return quoteToEdit;
    }

    public static void setQuoteToEdit(Quote quoteToEdit) {
        log.info("Setting quote to edit: " + quoteToEdit.getText());
        EditViewModel.quoteToEdit = quoteToEdit;
        EditViewModel.sourceToEdit = quoteToEdit.getSource();
    }
}
