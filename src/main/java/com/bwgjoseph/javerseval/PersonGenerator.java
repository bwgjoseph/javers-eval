package com.bwgjoseph.javerseval;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonGenerator {
    private Faker faker = new Faker();

    public Address generateAddress() {
        return Address.builder()
            .street(faker.address().streetName())
            .block(faker.address().buildingNumber())
            .postalCode(faker.address().zipCode())
            .build();
    }

    public CreditCard generatedCreditCard() {
        return CreditCard
            .builder()
            .cardNumber(new Random().nextLong())
            .cvv(new Random().nextInt())
            .expiry(LocalDate.now())
            .build();
    }

    public Person generate(int id) {
        return Person.builder()
            .id(id)
            .firstName(faker.name().firstName())
            .lastName(faker.name().lastName())
            .addresses(List.of(
                this.generateAddress(),
                this.generateAddress()
            ))
            .hobbies(List.of(
                faker.esports().game(),
                faker.esports().game(),
                faker.esports().game()
            ))
            // .gender(Gender.values()[new Random().nextInt(Gender.values().length)])
            .gender(Gender.MALE)
            // .isEmployed(new Random().nextBoolean())
            .isEmployed(true)
            .creditCard(this.generatedCreditCard())
            .lastUpdated(LocalDateTime.now())
            .build();
    }
}
