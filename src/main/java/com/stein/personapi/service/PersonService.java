package com.stein.personapi.service;

import com.stein.personapi.excpetion.PersonNotFoundException;
import com.stein.personapi.mapper.PersonMapper;
import com.stein.personapi.dto.MessageResponseDTO;
import com.stein.personapi.dto.request.PersonDTO;
import com.stein.personapi.entity.Person;
import com.stein.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = PersonMapper.INSTANCE.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return MessageResponseDTO.builder().message("Person created with id" + savedPerson.getId()).build();
    }

    public List<PersonDTO> ListAll() {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream().map(PersonMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return PersonMapper.INSTANCE.toDTO(person);
    }
}
