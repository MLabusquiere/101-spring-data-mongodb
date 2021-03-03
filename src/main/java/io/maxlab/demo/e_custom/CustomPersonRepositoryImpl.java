package io.maxlab.demo.e_custom;

import io.maxlab.demo.model.Person;
import io.maxlab.demo.model.PersonAggregate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import reactor.core.publisher.Flux;

public class CustomPersonRepositoryImpl implements CustomPersonRepositoryCustom {
  private final ReactiveMongoTemplate mongoTemplate;

  public CustomPersonRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Flux<PersonAggregate> findPersonAggregate() {
    GroupOperation groupFirstname =
            Aggregation.group("lastname").addToSet("$firstname").as("firstnames");
    return mongoTemplate.aggregate(
        Aggregation.newAggregation(Person.class, groupFirstname), PersonAggregate.class);
  }
}
