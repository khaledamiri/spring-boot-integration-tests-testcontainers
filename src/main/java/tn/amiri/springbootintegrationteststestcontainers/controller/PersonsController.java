package tn.amiri.springbootintegrationteststestcontainers.controller;

import org.springframework.web.bind.annotation.*;
import tn.amiri.springbootintegrationteststestcontainers.exceptions.NoPersonFoundException;
import tn.amiri.springbootintegrationteststestcontainers.model.Person;
import tn.amiri.springbootintegrationteststestcontainers.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonsController {

    private final PersonRepository personRepository;

    public PersonsController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable("id") Long id) {
        return personRepository.findById(id).orElseThrow(() -> new NoPersonFoundException("Person with id:" + id + " not found"));
    }

    @PostMapping
    public Person createNewPerson(@RequestBody Person person) {
        Person newPerson = new Person();
        if (person != null) {
            newPerson.setName(person.getName());
        }
        return personRepository.save(newPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePersonById(@PathVariable("id") Long id) {
        personRepository.deleteById(id);
    }
}