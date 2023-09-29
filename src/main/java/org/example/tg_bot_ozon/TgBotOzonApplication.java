package org.example.tg_bot_ozon;

import org.example.tg_bot_ozon.service.Bot;
import org.example.tg_bot_ozon.service.PersonService;
import org.example.tg_bot_ozon.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
public class TgBotOzonApplication
{
    public static void main(String[] args) {
        SpringApplication.run(TgBotOzonApplication.class, args);
    }

    @Bean
    public Bot getUser(@Value("${botToken}") String botToken, PersonService personService
            , ProductService productService) throws TelegramApiException {

        return new Bot(botToken, personService, productService);
    }
}
