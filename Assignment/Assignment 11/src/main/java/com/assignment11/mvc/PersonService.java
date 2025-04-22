package com.assignment11.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }
}
