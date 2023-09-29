package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Message;
import org.example.tg_bot_ozon.entity.Person;

import java.util.List;

public interface MessageService {
    List<Message> findAllByPerson(Person person);
}
