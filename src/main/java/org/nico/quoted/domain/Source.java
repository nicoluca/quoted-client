package org.nico.quoted.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor // Needed for JPA
@AllArgsConstructor
@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String title;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "source")
    //private Set<Quote> quotes;

    public Source(String title) {
        this.title = title;
    }

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
