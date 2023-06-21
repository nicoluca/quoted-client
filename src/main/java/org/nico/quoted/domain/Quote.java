package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter @Setter
public class Quote implements Identifiable, Serializable {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Paris")
    private Timestamp lastEdited; // TODO - Was previously used with @CreationTimestamp from HIbernate, now needs to come from server

    private String text;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Source source;

    @Builder
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
