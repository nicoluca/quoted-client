package org.nico.quoted.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter @Setter
public class Quote extends Identifiable {

    private Timestamp lastEdited; // TODO - Was previously used with @CreationTimestamp from HIbernate, now needs to come from server

    private String text;
    private Source source;

    public Quote(String text, Source source) {
        this.text = text;
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return text.equals(quote.text) &&
                source.equals(quote.source);
    }
}
