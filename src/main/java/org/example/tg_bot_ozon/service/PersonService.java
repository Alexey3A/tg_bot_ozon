package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Person;

import java.util.List;

public interface PersonService {
    Person savePerson(Person Person);
    List<Person> findAllPerson();
    Person findByTgUserID(Long id);
}
