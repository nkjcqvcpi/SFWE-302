package com.assignment12.mvc;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Person> people = personService.findAllPeople();
        model.addAttribute("people", people);
        return "home";
    }

    @GetMapping("/add-person")
    public String addPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "addPerson";
    }

    @PostMapping("/people")
    public String addPerson(@Valid Person person, Errors errors) {
        if (errors.hasErrors()) {
            return "addPerson";
        }
        personService.addPerson(person);

        return "redirect:/";
    }

    @ResponseBody
    @GetMapping(value = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getAllPeople() {
        return personService.findAllPeople();
    }

    @ResponseBody
    @PostMapping(value = "/api/add-person", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addPersonViaApi(@RequestBody Person person) {
        personService.addPerson(person);
        return "Added Person via REST API call";
    }
}
