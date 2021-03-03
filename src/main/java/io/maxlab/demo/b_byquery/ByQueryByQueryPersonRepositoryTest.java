package io.maxlab.demo.b_byquery;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.maxlab.demo.model.Person;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ByQueryByQueryPersonRepositoryTest {
  private static final Person PERSON_1 = Person.of("Peter", "Picnic");
  private static final Person PERSON_2 = Person.of("Maxence", "Labusquiere");
  @Autowired private ByQueryPersonRepository byQueryPersonRepository;

  @BeforeEach
  void setUp() {
    byQueryPersonRepository
        .deleteAll()
        .then(
            Mono.just(Set.of(PERSON_1, PERSON_2))
                .flatMapMany(byQueryPersonRepository::saveAll)
                .then())
        .as(StepVerifier::create)
        .verifyComplete();
  }

  @Test
  void findPersonByFirstnameAndLastnameAllIgnoreCase() {
    byQueryPersonRepository
        .findPersonByFirstnameAndLastnameAllIgnoreCase("peter", "picnic")
        .as(StepVerifier::create)
        .assertNext(p -> assertThat(p).isEqualTo(PERSON_1.withId(p.getId())))
        .verifyComplete();
  }
}
