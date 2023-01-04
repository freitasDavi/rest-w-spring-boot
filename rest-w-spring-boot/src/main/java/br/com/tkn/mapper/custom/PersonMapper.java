package br.com.tkn.mapper.custom;

import br.com.tkn.data.vo.v2.PersonVOV2;
import br.com.tkn.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
    public PersonVOV2 convertEntityToVo(Person person) {
        PersonVOV2 vo = new PersonVOV2();

        vo.setId(person.getId());
        vo.setAddress(person.getAddress());
        vo.setGender(person.getGender());
        vo.setBirthdate(new Date());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());

        return vo;
    }

    public Person convertVoToEntity(PersonVOV2 person) {
        Person entity = new Person();

        entity.setId(person.getId());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        //vo.setBirthdate(new Date());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());

        return entity;
    }
}
