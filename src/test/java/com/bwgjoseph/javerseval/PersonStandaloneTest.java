package com.bwgjoseph.javerseval;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
public class PersonStandaloneTest {
    private CompareService compareService;

    @BeforeAll
    public void beforeAll() {
        PersonGenerator pg = new PersonGenerator();
        Javers javers = JaversBuilder.javers().build();

        compareService = new CompareService(pg, javers);
    }

    @Test
    public void comparePerson() {
        Diff d = this.compareService.compare();

        System.out.println(d.getChangesByType(PropertyChange.class).size());

        // For any primative value, we can use ValueChange to print the change
        d.getChangesByType(ValueChange.class)
            .forEach(vc -> {
                // can I assume that without localId, it will be ValueObject?
                // and so I use the TypeName to display?
                // If this is Entity with @Id, localId will be that id value otherwise null
                // log.info("getAffectedLocalId {}", vc.getAffectedLocalId());
                // Based on @TypeName/@Id e.g Person/1
                // log.info("getAffectedGlobalId {}", vc.getAffectedGlobalId());
                // Based on @TypeName if not based on package path e.g Person
                // log.info("getAffectedGlobalId {}", vc.getAffectedGlobalId().getTypeName());
                // Optional[Person(id=1, firstName=Natosha, lastName=Koelpin, addresses=[Address(street=Rodrick Trace, postalCode=00530-4550, block=80151),
                // Address(street=Gutkowski Cape, postalCode=65085, block=1067)], hobbies=[StarCraft II, Hearthstone, Hearthstone],
                // gender=MALE, isEmployed=true, creditCard=CreditCard(cardNumber=-7694848501276982153, cvv=686382348, expiry=2021-08-11), lastUpdated=2021-08-11T21:38:24.167866400)]
                // log.info("getAffectedObject {}", vc.getAffectedObject());
                // log.info("getPropertyNameWithPath {}", vc.getPropertyNameWithPath());
                log.info("{}", vc.getChangeType());
                // To cater for new object, will probably need to check vc.getRight() == null too
                if (vc.getLeft() != null) {
                    log.info("{} changed from {} to {}", vc.getPropertyName(), vc.getLeft(), vc.getRight());
                } else {
                    log.info("{} added {}", vc.getPropertyName(), vc.getRight());
                }
            });

        // d.getChangesByType(InitialValueChange.class)
        //     .forEach(vc -> log.info("{} changed from {} to {}", vc.getPropertyName(), vc.getLeft(), vc.getRight()));

        d.getChangesByType(ListChange.class)
            .forEach(lc -> {
                // lc.get
                // lc.getChanges().forEach(action);
                // lc.getChanges().forEach(lcc -> log.info("x {}", lcc.toString()));
                lc.getValueAddedChanges()
                    .forEach(lca -> log.info("{} {}", lc.getPropertyName(), lca.getAddedValue().toString()));
            });

        // d.groupByObject().forEach(obj -> {
        //     // log.info("x {}", obj);
        //     log.info("global {}", obj.getGlobalId());
        //     log.info("changes {}", obj.getPropertyChanges());
        //     obj.getPropertyChanges().forEach(changes -> {
        //         changes.
        //     });
        //     // log.info("new {}", obj.getNewObjects());
        //     // log.info("removed {}", obj.getObjectsRemoved());
        // });

        // d.groupByObject().forEach(byObject -> {
        //     System.out.println("* changes on " +byObject.getGlobalId().value() + " : ");
        //     byObject.get().forEach(change -> System.out.println("  - " + change));
        // });

        // System.out.println(this.compareService.getJavers().getJsonConverter().toJson(d));

        // assertTrue(true);
    }
}
