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
import br.com.tkn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username);

        var user = repository.findByUserName(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }
}
