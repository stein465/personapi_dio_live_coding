package com.stein.personapi.service;

import com.stein.personapi.dto.MessageResponseDTO;
import com.stein.personapi.entity.Person;
import com.stein.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(Person person){
        Person savedPerson = personRepository.save(person);
        return MessageResponseDTO.builder().message("Person created with id" + savedPerson.getId()).build();
    }
}
