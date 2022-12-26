package br.com.tkn.services;

import br.com.tkn.exceptions.ResourceNotFoundException;
import br.com.tkn.model.Person;
import br.com.tkn.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll() {
        logger.info("Finding all people");

        return repository.findAll();
    }

    public Person findById(Long id) {
        logger.info("Finding one person");

        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
    }

    public Person create(Person person) {
        logger.info("Creating one person");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person");

        var entity = repository
                                .findById(person.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());

        return repository.save(entity);
    }

    public void delete(long id) {
        logger.info("Deleting one person");

        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
    private Person mockPerson(int i) {

        Person person = new Person();

        //person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastName("Last name " + i);
        person.setAddress("Some address in Brasil");
        person.setGender("Male");

        return person;
    }
}
