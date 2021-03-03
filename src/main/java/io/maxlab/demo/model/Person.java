package io.maxlab.demo.model;

import java.util.Objects;
import org.springframework.data.annotation.Id;

public class Person {
  @Id private final String id;
  private final String firstname;
  private final String lastname;

  Person(String id, String firstname, String lastname) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public static Person of(String firstname, String lastname) {
    return new Person(null, firstname, lastname);
  }

  public String getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public Person withId(String id) {
    return new Person(id, getFirstname(), getLastname());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(id, person.id)
        && Objects.equals(firstname, person.firstname)
        && Objects.equals(lastname, person.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname);
  }

  @Override
  public String toString() {
    return "Person{"
        + "id='"
        + id
        + '\''
        + ", firstname='"
        + firstname
        + '\''
        + ", lastname='"
        + lastname
        + '\''
        + '}';
  }
}
