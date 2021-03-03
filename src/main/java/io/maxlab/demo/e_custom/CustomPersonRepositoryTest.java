package io.maxlab.demo.e_custom;

import io.maxlab.demo.model.Person;
import io.maxlab.demo.model.PersonAggregate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class CustomPersonRepositoryTest {
  private static final Person PERSON_1 = Person.of("Peter", "Picnic");
  private static final Person PERSON_2 = Person.of("Maxence", "Picnic");
  @Autowired private CustomPersonRepository customPersonRepository;

  @BeforeEach
  void setUp() {
    customPersonRepository
        .deleteAll()
        .then(
            Mono.just(Set.of(PERSON_1, PERSON_2))
                .flatMapMany(customPersonRepository::saveAll)
                .then())
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void aggregateAllFirstnames() {
    customPersonRepository
        .findPersonAggregate()
        .as(StepVerifier::create)
        .expectNext(new PersonAggregate("Picnic", Set.of("Peter", "Maxence")))
        .verifyComplete();
  }
}
