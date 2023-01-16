package br.com.tkn.unitytests.mockito.services;

import br.com.tkn.data.vo.v1.PersonVO;
import br.com.tkn.unitytests.mapper.mocks.MockPerson;
import br.com.tkn.exceptions.RequiredObjectIsNullException;
import br.com.tkn.model.Person;
import br.com.tkn.repositories.PersonRepository;
import br.com.tkn.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTeste {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        Person entity = input.mockEntity(1);

        entity.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
    }

    @Test
    void testCreate() throws Exception {
        Person entity = input.mockEntity(1);

        Person persisted = entity;
        entity.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() throws Exception {

        Exception exception = Assertions.assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() throws Exception {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Addres Test1", result.getAddress());
        Assertions.assertEquals("First Name Test1", result.getFirstName());
        Assertions.assertEquals("Last Name Test1", result.getLastName());
        Assertions.assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() throws Exception {

        Exception exception = Assertions.assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() throws Exception {
        Person entity = input.mockEntity(1);
        entity.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));

       service.delete(1L);
    }

    @Test
    void testFindAll() {
        List<Person> list = input.mockEntityList();

        Mockito.when(repository.findAll()).thenReturn(list);

        var people = service.findAll();

        Assertions.assertNotNull(people);
        Assertions.assertEquals(14, people.size());

        var personOne = people.get(1);

        Assertions.assertNotNull(personOne);
        Assertions.assertNotNull(personOne.getKey());
        Assertions.assertNotNull(personOne.getLinks());
        Assertions.assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Addres Test1", personOne.getAddress());
        Assertions.assertEquals("First Name Test1", personOne.getFirstName());
        Assertions.assertEquals("Last Name Test1", personOne.getLastName());
        Assertions.assertEquals("Female", personOne.getGender());

        var personFour = people.get(4);

        Assertions.assertNotNull(personFour);
        Assertions.assertNotNull(personFour.getKey());
        Assertions.assertNotNull(personFour.getLinks());
        Assertions.assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\""));
        Assertions.assertEquals("Addres Test4", personFour.getAddress());
        Assertions.assertEquals("First Name Test4", personFour.getFirstName());
        Assertions.assertEquals("Last Name Test4", personFour.getLastName());
        Assertions.assertEquals("Male", personFour.getGender());

        var personSeven = people.get(7);

        Assertions.assertNotNull(personSeven);
        Assertions.assertNotNull(personSeven.getKey());
        Assertions.assertNotNull(personSeven.getLinks());
        Assertions.assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\""));
        Assertions.assertEquals("Addres Test7", personSeven.getAddress());
        Assertions.assertEquals("First Name Test7", personSeven.getFirstName());
        Assertions.assertEquals("Last Name Test7", personSeven.getLastName());
        Assertions.assertEquals("Female", personSeven.getGender());
    }
}
