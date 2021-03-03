package io.maxlab.demo.e_custom;

import io.maxlab.demo.model.PersonAggregate;
import reactor.core.publisher.Flux;

public interface CustomPersonRepositoryCustom {
  Flux<PersonAggregate> findPersonAggregate();
}
