package org.nico.quoted.model;

import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Quote;
import org.nico.quoted.domain.Source;

/*
    A helper class to store the source and quote to edit.
 */

@Slf4j
public class EditViewModel {
    private Source sourceToEdit;
    private Quote quoteToEdit;

    public Source getSourceToEdit() {
        return sourceToEdit;
    }

    public void setSourceToEdit(Source sourceToEdit) {
        if (sourceToEdit == null)
            log.info("Setting source to edit: null");
        else
            log.info("Setting source to edit: " + sourceToEdit.getTitle());
        this.sourceToEdit = sourceToEdit;
    }

    public Quote getQuoteToEdit() {
        return quoteToEdit;
    }

    public void setQuoteToEdit(Quote quoteToEdit) {
        log.info("Setting quote to edit: " + quoteToEdit.getText());
        this.quoteToEdit = quoteToEdit;
        this.sourceToEdit = quoteToEdit.getSource();
    }
}
