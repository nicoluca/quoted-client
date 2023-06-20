package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public interface Identifiable {
    long getId();
    void setId(long id);
}
