package org.example.tg_bot_ozon.repository;

import org.example.tg_bot_ozon.entity.Message;
import org.example.tg_bot_ozon.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByPerson(Person person);
    //   @Query("SELECT m FROM Message m WHERE m.message_text = ?1 and m.person_id = ?2")
    List<Message> findAllByMessageTextAndPerson(String messageText, Person person);
}
