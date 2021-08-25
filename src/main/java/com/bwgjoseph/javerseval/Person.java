package com.bwgjoseph.javerseval;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@TypeName("Person")
public class Person implements Serializable {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private List<Address> addresses;
    private List<String> hobbies;
    private Gender gender;
    private boolean isEmployed;
    // This is considered as ValueObject
    private CreditCard creditCard;
    private LocalDateTime lastUpdated;
}
