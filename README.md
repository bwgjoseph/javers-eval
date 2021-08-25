# Javers Eval

This repository is made to evaluate [JaVers](https://javers.org/) on whether it can be used to perform Object Diff specific to my use-case

Given two entity, it needs to traverse the entire structure and compare against different type of data structure such as primitive, custom object, list of primitive, list of custom object, and so on and to return a list of changes (delta)

After which, it should also expose enough information from the API to grab

- `before` and `after` value given a change of same object
- `new` value given a addition
- `removed` value given a removal

If it's a object or list of object, it also needs to be able to group/classify those changes as a single unit/grouping

Lastly, to output as a user-friendly change description based on the changes between the two object diff

---

Given the following data structure

```java
class Person implements Serializable {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private List<Address> addresses;
    private List<String> hobbies;
    private Gender gender;
    private boolean isEmployed;
    private CreditCard creditCard;
    private LocalDateTime lastUpdated;
}

class Address implements Serializable {
    private String street;
    private String postalCode;
    private String block;
}

class CreditCard implements Serializable {
    private long cardNumber;
    private int cvv;
    private LocalDate expiry;
}
```

The output would be something of this nature

```
// knowing there's a new address added to the List<Address>
New address added:
  - street is Cesar Extensions, postalCode is 47795, block is 056
// if removed, it can be
Existing address removed:
  - street is Cesar Extensions, postalCode is 47795, block is 056
// CreditCard is an Object (Value Object?)
Existing CreditCard Info updated:
  - cardNumber changed from 4240975573591258006 to -585401770101026848, cvv changed from 1146085061 to -2006036587
// and the rest of the primitive data type
gender changed from MALE to FEMALE
isEmployed changed from true to false
```

## Test

Look at [PersonStandaloneTest](./src/test/java/com/bwgjoseph/javerseval/PersonStandaloneTest.java) on the current implementation/test progress

## Reference Code

```java
diff.getChangesByType(PropertyChange.class)
    .forEach(pc -> log.info("{} changes to {}", pc.getPropertyName(), pc.getAffectedObject()));

diff.groupByObject().forEach(byObject -> {
    System.out.println("* changes on " +byObject.getGlobalId().value() + " : ");
    byObject.get().forEach(change -> System.out.println("  - " + change));
});

diff.getChangesByType(ListChange.class).forEach(lc -> {
  log.info("{}: Removed: {}, Added: {}", lc.getPropertyName(), lc.getRemovedValues().toString(), lc.getAddedValues().toString());
});

class XComparator implements CustomValueComparator<X> {
  @Override
  public String toString(X x) { return x.getXName(); }
}
```

## References

- [JaVers complex @valueobject comparator](https://gist.github.com/hank-cp/3db40faed1dd9f02ababd86c2c9eaf8d)
- [how-to-use-javers-to-diff-two-object-of-same-entity-class-but-with-different-id](https://stackoverflow.com/questions/38519401/how-to-use-javers-to-diff-two-object-of-same-entity-class-but-with-different-id)
- [compare-value-objects-using-a-custom-comparator](https://stackoverflow.com/questions/47607126/compare-value-objects-using-a-custom-comparator)
- [Grouping change description](https://github.com/javers/javers/discussions/1128)

