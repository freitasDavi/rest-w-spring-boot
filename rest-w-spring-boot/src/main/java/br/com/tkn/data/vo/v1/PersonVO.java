package br.com.tkn.data.vo.v1;


import com.github.dozermapper.core.Mapping;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "first_name", "last_name", "gender", "address"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Mapping("id")
    @JsonProperty("id")
    private Long key;

    //@JsonProperty("first_name")
    private String firstName;
    
    //@JsonProperty("last_name")
    private String lastName;
    
    private String address;
    
    //@JsonIgnore
    private String gender;

    public PersonVO() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonVO personVO = (PersonVO) o;
        return Objects.equals(key, personVO.key) && Objects.equals(firstName, personVO.firstName) && Objects.equals(lastName, personVO.lastName) && Objects.equals(address, personVO.address) && Objects.equals(gender, personVO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, firstName, lastName, address, gender);
    }
}
