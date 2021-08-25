package com.bwgjoseph.javerseval;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompareService {
	private final PersonGenerator personGenerator;
	private final Javers javers;

    public Diff compare() {
        Person a = this.personGenerator.generate(1);
        Person b = SerializationUtils.clone(a);

        // b.setFirstName("Joseph");
        // b.setLastName("Johnson");
        b.setGender(Gender.FEMALE);
        b.setEmployed(false);
        // b.setLastUpdated(LocalDateTime.now());
        b.setHobbies(List.of(
            "a",
            "B",
            "C"
        ));
        b.setCreditCard(this.personGenerator.generatedCreditCard());
        b.setAddresses(List.of(a.getAddresses().get(0), a.getAddresses().get(1), this.personGenerator.generateAddress()));

        return javers.compare(a, b);
    }

    public Javers getJavers() {
        return this.javers;
    }
}
