package io.maxlab.demo.c_byexample;

import io.maxlab.demo.model.Person;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ByExamplePersonRepository extends Repository<Person, String> {
  Flux<Person> findAll(Example<Person> example);

  Mono<Void> deleteAll();

  Flux<Person> saveAll(Iterable<Person> person);
}
