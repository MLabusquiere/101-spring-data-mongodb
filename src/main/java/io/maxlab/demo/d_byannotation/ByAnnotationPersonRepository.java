package io.maxlab.demo.d_byannotation;

import io.maxlab.demo.model.Person;
import io.maxlab.demo.model.PersonAggregate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ByAnnotationPersonRepository extends Repository<Person, String> {
  Mono<Void> deleteAll();

  Flux<Person> saveAll(Iterable<Person> person);

  @Query("{ firstname: '?0' }")
  Flux<Person> findByFirstname(String lastname);

  @Aggregation("{ $group: { _id : $lastname, firstnames : { $addToSet : $firstname } } }")
  Flux<PersonAggregate> findPersonAggregate();
}
