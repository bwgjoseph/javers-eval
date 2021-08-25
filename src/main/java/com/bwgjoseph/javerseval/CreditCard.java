package com.bwgjoseph.javerseval;

import java.io.Serializable;
import java.time.LocalDate;

import org.javers.core.metamodel.annotation.TypeName;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@TypeName("CreditCard")
public class CreditCard implements Serializable {
    private long cardNumber;
    private int cvv;
    private LocalDate expiry;
}
