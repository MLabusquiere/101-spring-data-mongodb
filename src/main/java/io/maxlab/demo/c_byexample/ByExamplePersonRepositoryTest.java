package io.maxlab.demo.c_byexample;

import static org.assertj.core.api.Assertions.assertThat;

import io.maxlab.demo.model.Person;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ByExamplePersonRepositoryTest {
  private static final Person PERSON_1 = Person.of("Peter", "Picnic");
  private static final Person PERSON_2 = Person.of("Maxence", "Labusquiere");
  @Autowired private ByExamplePersonRepository byExamplePersonRepository;

  @BeforeEach
  void setUp() {
    byExamplePersonRepository
        .deleteAll()
        .then(
            Mono.just(Set.of(PERSON_1, PERSON_2))
                .flatMapMany(byExamplePersonRepository::saveAll)
                .then())
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void findAllByExample() {
    byExamplePersonRepository
        .findAll(Example.of(Person.of("Peter", null)))
        .as(StepVerifier::create)
        .assertNext(p -> assertThat(p).isEqualTo(PERSON_1.withId(p.getId())))
        .verifyComplete();
  }

  @Test
  void findAllByExampleIgnoreCase() {
    byExamplePersonRepository
        .findAll(Example.of(Person.of("peter", null), ExampleMatcher.matching().withIgnoreCase()))
        .as(StepVerifier::create)
        .assertNext(p -> assertThat(p).isEqualTo(PERSON_1.withId(p.getId())))
        .verifyComplete();
  }
}
