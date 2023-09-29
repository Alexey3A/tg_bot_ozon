package org.example.tg_bot_ozon.repository;

import org.example.tg_bot_ozon.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByTgUserID(Long id);
}
