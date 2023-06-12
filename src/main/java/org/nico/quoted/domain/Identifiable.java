package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Identifiable {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id;
}
