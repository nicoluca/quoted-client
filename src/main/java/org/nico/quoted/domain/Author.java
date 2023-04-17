package org.nico.quoted.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class Author {
    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return this.firstName.equalsIgnoreCase(author.firstName)
                && this.lastName.equalsIgnoreCase(author.lastName);
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
