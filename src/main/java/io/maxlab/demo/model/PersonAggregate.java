package io.maxlab.demo.model;

import java.util.Objects;
import java.util.Set;
import org.springframework.data.annotation.Id;

public class PersonAggregate {
    @Id private final String lastname;
    private final Set<String> firstnames;

    public PersonAggregate(String lastname, Set<String> firstnames) {
        this.lastname = lastname;
    this.firstnames = firstnames;
    }

    public String getLastname() {
        return lastname;
    }

    public Set<String> getFirstnames() {
        return firstnames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonAggregate that = (PersonAggregate) o;
        return Objects.equals(lastname, that.lastname) && Objects.equals(firstnames, that.firstnames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastname, firstnames);
    }

    @Override
    public String toString() {
        return "PersonAggregate{" +
                "lastname='" + lastname + '\'' +
                ", firstnames=" + firstnames +
                '}';
    }
}
