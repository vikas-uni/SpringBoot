package com.baeldung.reactive.webclient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
}
