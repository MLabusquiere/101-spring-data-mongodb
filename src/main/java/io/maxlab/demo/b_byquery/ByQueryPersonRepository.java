package io.maxlab.demo.b_byquery;

import io.maxlab.demo.model.Person;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ByQueryPersonRepository extends Repository<Person, String> {
  Flux<Person> findPersonByFirstnameAndLastnameAllIgnoreCase(String firstname, String lastname);

  Mono<Void> deleteAll();

  Flux<Person> saveAll(Iterable<Person> person);
}
