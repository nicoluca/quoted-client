package org.nico.quoted.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public abstract class Source {
    private String title;
    public static final SourceType type = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return this.title.equals(source.title);
    }

    public abstract String getOrigin();

    @Override
    public String toString() {
        return this.getTitle() + " (" + this.getOrigin() + ")";
    }
}
