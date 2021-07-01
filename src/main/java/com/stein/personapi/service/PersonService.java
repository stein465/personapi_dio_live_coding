package com.stein.personapi.service;

import com.stein.personapi.excpetion.PersonNotFoundException;
import com.stein.personapi.mapper.PersonMapper;
import com.stein.personapi.dto.MessageResponseDTO;
import com.stein.personapi.dto.request.PersonDTO;
import com.stein.personapi.entity.Person;
import com.stein.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = PersonMapper.INSTANCE.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return CreateMessageResponse(savedPerson.getId(), "Created person with id ");
    }

    public List<PersonDTO> ListAll() {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream().map(PersonMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return PersonMapper.INSTANCE.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }


    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToUpdate = PersonMapper.INSTANCE.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);
        return CreateMessageResponse(updatedPerson.getId(), "Updated person with id ");
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO CreateMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
