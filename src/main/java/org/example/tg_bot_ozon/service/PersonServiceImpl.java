package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Person;
import org.example.tg_bot_ozon.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> findAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public Person findByTgUserID(Long id) {
        return personRepository.findByTgUserID(id);
    }
}
