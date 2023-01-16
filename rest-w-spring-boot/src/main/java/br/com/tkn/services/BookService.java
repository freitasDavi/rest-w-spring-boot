package br.com.tkn.services;

import br.com.tkn.controllers.BookController;
import br.com.tkn.data.vo.v1.BookVO;
import br.com.tkn.exceptions.RequiredObjectIsNullException;
import br.com.tkn.exceptions.ResourceNotFoundException;
import br.com.tkn.mapper.DozerMapper;
import br.com.tkn.model.Book;
import br.com.tkn.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookVO> findAll() {
        logger.info("Finding all books");

        var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);

        books.stream().forEach(p -> {
            try {
                p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return books;
    }

    public BookVO findById(Long id) throws Exception {
        logger.info("Finding one book");

        var entity = repository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public BookVO create(BookVO book) throws Exception {

        if (book == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one book");

        var newEntity = DozerMapper.parseObject(book, Book.class);

        var bookVO = DozerMapper.parseObject(repository.save(newEntity), BookVO.class);

        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());

        return bookVO;
    }

    public BookVO update(BookVO book) throws Exception {
        if (book == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Updating one book");

        var entity = repository
                                .findById(book.getKey())
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setTitle(book.getTitle());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(long id) {
        logger.info("Deleting one book");

        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
