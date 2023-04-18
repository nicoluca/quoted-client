package org.nico.quoted.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
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
}
