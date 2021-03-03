package io.maxlab.demo.a_simple;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.maxlab.demo.model.Person;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class SimplePersonRepositoryTest {
  private static final Person PERSON_1 = Person.of("Peter", "Picnic");
  private static final Person PERSON_2 = Person.of("Maxence", "Labusquiere");
  public static final Sort BY_FIRSTNAME_ASC =
      Sort.sort(Person.class).by(Person::getFirstname).ascending();
  @Autowired private SimplePersonRepository simplePersonRepository;

  @BeforeEach
  void setUp() {
    simplePersonRepository.deleteAll().as(StepVerifier::create).verifyComplete();
  }

  @Test
  void findById() {
    Mono.just(PERSON_1)
        .flatMap(simplePersonRepository::insert)
        .map(Person::getId)
        .flatMap(simplePersonRepository::findById)
        .as(StepVerifier::create)
        .assertNext(p -> assertThat(p).isEqualTo(PERSON_1.withId(p.getId())))
        .verifyComplete();
  }

  @Test
  void findAll() {
    Mono.just(Set.of(PERSON_1, PERSON_2))
        .flatMapMany(simplePersonRepository::saveAll)
        .thenMany(simplePersonRepository.findAll(BY_FIRSTNAME_ASC))
        .as(StepVerifier::create)
        .assertNext(p -> assertThat(p).isEqualTo(PERSON_2.withId(p.getId())))
        .assertNext(p -> assertThat(p).isEqualTo(PERSON_1.withId(p.getId())))
        .verifyComplete();
  }
}
