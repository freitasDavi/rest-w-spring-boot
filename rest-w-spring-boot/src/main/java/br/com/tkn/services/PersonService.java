package br.com.tkn.services;

import br.com.tkn.controllers.PersonController;
import br.com.tkn.data.vo.v1.PersonVO;
import br.com.tkn.data.vo.v2.PersonVOV2;
import br.com.tkn.exceptions.RequiredObjectIsNullException;
import br.com.tkn.exceptions.ResourceNotFoundException;
import br.com.tkn.mapper.DozerMapper;
import br.com.tkn.mapper.custom.PersonMapper;
import br.com.tkn.model.Person;
import br.com.tkn.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people");

        var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);

        persons.stream().forEach(p -> {
            try {
                p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return persons;
    }

    public PersonVO findById(Long id) throws Exception {
        logger.info("Finding one person");

        var entity = repository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public PersonVO create(PersonVO person) throws Exception {

        if (person == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one person");

        var newEntity = DozerMapper.parseObject(person, Person.class);

        var personVO = DozerMapper.parseObject(repository.save(newEntity), PersonVO.class);

        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());

        return personVO;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person");

        var newEntity = mapper.convertVoToEntity(person);

        var personVO = mapper.convertEntityToVo(repository.save(newEntity));

        //personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return personVO;
    }

    public PersonVO update(PersonVO person) throws Exception {
        if (person == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Updating one person");

        var entity = repository
                                .findById(person.getKey())
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());

        var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void delete(long id) {
        logger.info("Deleting one person");

        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
