package org.nico.quoted.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    @ManyToOne
    private Source source;

    public Quote(String text, Source source) {
        this.text = text;
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public Source getSource() {
        return source;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource(Source source) {
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
