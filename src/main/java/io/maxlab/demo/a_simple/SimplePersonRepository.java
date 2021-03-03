package io.maxlab.demo.a_simple;

import io.maxlab.demo.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SimplePersonRepository extends ReactiveMongoRepository<Person, String> {}

