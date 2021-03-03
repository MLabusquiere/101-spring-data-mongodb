package io.maxlab.demo.e_custom;

import io.maxlab.demo.model.Person;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomPersonRepository extends Repository<Person, String>, CustomPersonRepositoryCustom {
  Mono<Void> deleteAll();

  Flux<Person> saveAll(Iterable<Person> person);
}
