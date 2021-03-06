package by.intro.helloworldreactive.controller;

import by.intro.helloworldreactive.model.Person;
import by.intro.helloworldreactive.service.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

class PersonControllerTest {

    @Test
    void shouldReturnSixPerson() {
        Person[] persons = new Person[]{
                createPerson(1L), createPerson(2L),
                createPerson(3L), createPerson(4L),
                createPerson(5L), createPerson(6L),
                createPerson(7L), createPerson(8L),
                createPerson(9L), createPerson(10L)
        };
        Flux<Person> personFlux = Flux.just(persons);

        PersonService personService = Mockito.mock(PersonService.class);
        Mockito.when(personService.getAllPersons()).thenReturn(personFlux);

        WebTestClient testClient = WebTestClient.bindToController(
                new PersonController(personService))
                .build();

        testClient.get().uri("/api/v1/persons/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(persons[0].getId().toString())
                .jsonPath("$[0].name").isEqualTo("Person 1")
                .jsonPath("$[1].id").isEqualTo(persons[1].getId().toString())
                .jsonPath("$[1].name").isEqualTo("Person 2")
                .jsonPath("$[5].id").isEqualTo(persons[5].getId().toString())
                .jsonPath("$[5].name").isEqualTo("Person 6")
                .jsonPath("$[6]").doesNotExist();
    }

    @Test
    public void shouldSavePerson() {
        PersonService personService = Mockito.mock(PersonService.class);
        Mono<Person> unsavedPersonMono = Mono.just(createPerson(null));
        Person savedPerson = createPerson(null);
        Mono<Person> savedTacoMono = Mono.just(savedPerson);
        Mockito.when(personService.addPerson(any())).thenReturn(savedTacoMono);
        WebTestClient testClient = WebTestClient.bindToController(
                new PersonController(personService)).build();
        testClient.post()
                .uri("/api/v1/persons/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedPersonMono, Person.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Person.class)
                .isEqualTo(savedPerson);
    }


    private Person createPerson(Long number) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("Person " + number);
        person.setAge(new Random().nextInt(80));
        person.setEmail(person.getName() + "@email.com");
        return person;
    }

}