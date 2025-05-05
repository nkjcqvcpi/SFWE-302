package com.assignment12.mvc;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Adding Person via RabbitMQ Console");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter person's name: ");
        String name = scanner.nextLine();

        System.out.println("Enter person's email: ");
        String email = scanner.nextLine();

        System.out.println("Enter person's age: ");
        int age = scanner.nextInt();

        scanner.close();

        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setAge(age);

        System.out.println("Sending person...");
        rabbitTemplate.convertAndSend(MvcApplication.topicExchangeName, "foo.bar.baz", person);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
