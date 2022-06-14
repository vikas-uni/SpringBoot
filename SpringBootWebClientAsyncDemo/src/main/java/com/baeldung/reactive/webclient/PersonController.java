package com.baeldung.reactive.webclient;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {


    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Person> findPersonsStream() {
        return Flux.fromStream(this::prepareStream).delayElements(Duration.ofSeconds(1))/*delaySequence(Duration.ofSeconds(1))*/
                .doOnNext(person -> log.info("Server produces: {}", person));
    }
    
    private Stream<Person> prepareStream() {
        return Stream.of(
            new Person(1, "Name01", "Surname01", 11),
            new Person(2, "Name02", "Surname02", 22),
            new Person(3, "Name03", "Surname03", 33),
            new Person(4, "Name04", "Surname04", 44),
            new Person(5, "Name05", "Surname05", 55),
            new Person(6, "Name06", "Surname06", 66),
            new Person(7, "Name07", "Surname07", 77),
            new Person(8, "Name08", "Surname08", 88),
            new Person(9, "Name09", "Surname09", 99)
        );
    }
}