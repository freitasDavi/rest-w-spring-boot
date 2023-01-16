package br.com.tkn.unitytests.mockito.services;

import br.com.tkn.data.vo.v1.BookVO;
import br.com.tkn.exceptions.RequiredObjectIsNullException;
import br.com.tkn.model.Book;
import br.com.tkn.repositories.BookRepository;
import br.com.tkn.services.BookService;
import br.com.tkn.unitytests.mapper.mocks.MockBook;
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
class BookServicesTeste {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        Book entity = input.mockEntity(1);

        entity.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Some Author1", result.getAuthor());
        Assertions.assertEquals("Some Title1", result.getTitle());
        Assertions.assertEquals(25D, result.getPrice());
        Assertions.assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreate() throws Exception {
        Book entity = input.mockEntity(1);

        Book persisted = entity;
        entity.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        Assertions.assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Some Author1", result.getAuthor());
        Assertions.assertEquals("Some Title1", result.getTitle());
        Assertions.assertEquals(25D, result.getPrice());
        Assertions.assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() throws Exception {

        Exception exception = Assertions.assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getKey());
        Assertions.assertNotNull(result.getLinks());
        System.out.println("TESTE UPDATE");
        System.out.println(result.toString());
        System.out.println("TESTE UPDATE");
        Assertions.assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Some Author1", result.getAuthor());
        Assertions.assertEquals("Some Title1", result.getTitle());
        Assertions.assertEquals(25D, result.getPrice());
        Assertions.assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBook() throws Exception {

        Exception exception = Assertions.assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete() throws Exception {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));

       service.delete(1L);
    }

    @Test
    void testFindAll() {
        List<Book> list = input.mockEntityList();

        Mockito.when(repository.findAll()).thenReturn(list);

        var people = service.findAll();

        Assertions.assertNotNull(people);
        Assertions.assertEquals(14, people.size());

        var bookOne = people.get(1);

        Assertions.assertNotNull(bookOne);
        Assertions.assertNotNull(bookOne.getKey());
        Assertions.assertNotNull(bookOne.getLinks());
        Assertions.assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\""));
        Assertions.assertEquals("Some Author1", bookOne.getAuthor());
        Assertions.assertEquals("Some Title1", bookOne.getTitle());
        Assertions.assertEquals(25D, bookOne.getPrice());
        Assertions.assertNotNull(bookOne.getLaunchDate());

        var bookFour = people.get(4);

        Assertions.assertNotNull(bookFour);
        Assertions.assertNotNull(bookFour.getKey());
        Assertions.assertNotNull(bookFour.getLinks());
        Assertions.assertTrue(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\""));
        Assertions.assertEquals("Some Author4", bookFour.getAuthor());
        Assertions.assertEquals("Some Title4", bookFour.getTitle());
        Assertions.assertEquals(25D, bookFour.getPrice());
        Assertions.assertNotNull(bookFour.getLaunchDate());

        var bookSeven = people.get(7);

        Assertions.assertNotNull(bookSeven);
        Assertions.assertNotNull(bookSeven.getKey());
        Assertions.assertNotNull(bookSeven.getLinks());
        Assertions.assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\""));
        Assertions.assertEquals("Some Author7", bookSeven.getAuthor());
        Assertions.assertEquals("Some Title7", bookSeven.getTitle());
        Assertions.assertEquals(25D, bookSeven.getPrice());
        Assertions.assertNotNull(bookSeven.getLaunchDate());
    }
}
