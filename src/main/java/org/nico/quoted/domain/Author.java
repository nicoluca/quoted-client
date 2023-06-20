package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor // Needed for JPA
@Getter @Setter
public class Author {

    private String firstName;
    private String lastName;

    @JsonIgnore
    private Set<Book> books;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return firstName.equalsIgnoreCase(author.firstName) &&
        lastName.equalsIgnoreCase(author.lastName);
    }
}