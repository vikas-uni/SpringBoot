package pl.piomin.services.model;

import lombok.*;

@Getter
@Setter
@ToString
public class Person {

    
	private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    
    public Person(int i, String string, String string2, int j) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

}
