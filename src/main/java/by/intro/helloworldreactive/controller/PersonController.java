package by.intro.helloworldreactive.controller;

import by.intro.helloworldreactive.dto.PersonDto;
import by.intro.helloworldreactive.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

    @GetMapping("/men")
    public Flux<PersonDto> getMen() {
        return personService.getMen();
    }

    @GetMapping("/all")
    public Flux<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/groups")
    public Flux<Object> getGroups() {
        return personService.getGroupsByNameLength();
    }

    @GetMapping("/new-email")
    public Flux<PersonDto> getAllPersonsWithChangedEmail() {
        return personService.getAllPersonsWithChangedEmail();
    }

    @GetMapping("/oldest")
    public Mono<PersonDto> getOldestPerson() {
        return personService.getOldestPerson();
    }


}
