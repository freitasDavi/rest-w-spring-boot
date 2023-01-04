package br.com.tkn.services;

import br.com.tkn.data.vo.v1.PersonVO;
import br.com.tkn.data.vo.v2.PersonVOV2;
import br.com.tkn.exceptions.ResourceNotFoundException;
import br.com.tkn.mapper.DozerMapper;
import br.com.tkn.mapper.custom.PersonMapper;
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

    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people");

        return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person");

        var entity = repository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Creating one person");

        var newEntity = DozerMapper.parseObject(person, Person.class);

        var personVO = DozerMapper.parseObject(repository.save(newEntity), PersonVO.class);

        return personVO;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person");

        var newEntity = mapper.convertVoToEntity(person);

        var personVO = mapper.convertEntityToVo(repository.save(newEntity));

        return personVO;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Updating one person");

        var entity = repository
                                .findById(person.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());

        return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(long id) {
        logger.info("Deleting one person");

        var entity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
