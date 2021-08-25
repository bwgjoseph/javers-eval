package com.bwgjoseph.javerseval;

import java.io.Serializable;

import org.javers.core.metamodel.annotation.TypeName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@TypeName("Address")
public class Address implements Serializable {
    private String street;
    private String postalCode;
    private String block;
}
