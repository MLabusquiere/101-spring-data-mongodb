package io.maxlab.demo.d_byannotation;

import io.maxlab.demo.model.Person;
import io.maxlab.demo.model.PersonAggregate;
import java.util.Set;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ByAnnotationPersonRepositoryTest {
  private static final Person PERSON_1 = Person.of("Peter", "Picnic");
  private static final Person PERSON_2 = Person.of("Maxence", "Picnic");
  @Autowired private ByAnnotationPersonRepository byAnnotationPersonRepository;

  @BeforeEach
  void setUp() {
    byAnnotationPersonRepository
        .deleteAll()
        .then(
            Mono.just(Set.of(PERSON_1, PERSON_2))
                .flatMapMany(byAnnotationPersonRepository::saveAll)
                .then())
        .as(StepVerifier::create)
        .verifyComplete();
  }


  @Test
  void findByFirstname() {
    byAnnotationPersonRepository
            .findByFirstname("Peter")
            .as(StepVerifier::create)
            .assertNext(p -> AssertionsForClassTypes.assertThat(p).isEqualTo(PERSON_1.withId(p.getId())))
            .verifyComplete();
  }

  @Test
  void aggregateAllFirstnames() {
    byAnnotationPersonRepository
            .findPersonAggregate()
            .as(StepVerifier::create)
            .expectNext(new PersonAggregate("Picnic", Set.of("Peter", "Maxence")))
            .verifyComplete();
  }
}
