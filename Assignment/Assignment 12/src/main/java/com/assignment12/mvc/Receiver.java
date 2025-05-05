package com.assignment12.mvc;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final CountDownLatch latch = new CountDownLatch(1);
    private final PersonService personService;

    public Receiver(PersonService personService) {
        this.personService = personService;
    }

    public void receiveMessage(Person person) {
        System.out.println("RabbitMQ Received <" + person.getName() + ", " + person.getEmail() + ", " + person.getAge() + ">");
        personService.addPerson(person);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
