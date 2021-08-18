package by.intro.helloworldreactive.service;

import by.intro.helloworldreactive.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    List<PersonDto> men = Arrays.asList(
            new PersonDto("Alex", 18, "alex@email.com"),
            new PersonDto("Bob", 32, "bob@email.com")
    );
    List<PersonDto> women = Arrays.asList(
            new PersonDto("Lina", 44, "lina@email.com"),
            new PersonDto("Regina", 20, "regina@email.com")
    );

    public Flux<PersonDto> getMen() {
        return Flux.fromIterable(men);
    }

    public Flux<PersonDto> getAllPersons() {
        return Flux.merge(Flux.fromIterable(men), Flux.fromIterable(women));
    }

    public Flux<Object> getGroupsByNameLength() {
        return Flux.merge(Flux.fromIterable(men), Flux.fromIterable(women))
                .groupBy(personDto -> personDto.getName().length())
                .concatMap(Flux::collectList);
    }

    public Flux<PersonDto> getAllPersonsWithChangedEmail() {
        return Flux.merge(Flux.fromIterable(men), Flux.fromIterable(women))
                .map(personDto -> {
                    personDto.setEmail(personDto.getEmail().replaceAll("email.com", "company.com"));
                    return personDto;
                });
    }

    public Mono<PersonDto> getOldestPerson() {
        return Flux.merge(Flux.fromIterable(men), Flux.fromIterable(women))
                .sort(PersonDto::compareTo)
                .last();
    }

}
