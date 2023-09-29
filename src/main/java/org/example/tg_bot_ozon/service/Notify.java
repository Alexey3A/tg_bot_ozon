package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Message;
import org.example.tg_bot_ozon.entity.Person;
import org.example.tg_bot_ozon.entity.Product;
import org.example.tg_bot_ozon.entity.RequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Notify {

    private final PersonService personService;
    private final ProductService productService;
    private final RequestDetailsService requestDetailsService;
    private final Bot bot;

    @Autowired
    public Notify(PersonService personService, ProductService productService, RequestDetailsService requestDetailsService, Bot bot) {
        this.personService = personService;
        this.productService = productService;
        this.requestDetailsService = requestDetailsService;
        this.bot = bot;
    }

    public void notificationForPerson(){
        List<Person> personList = personService.findAllPerson();
        List<Product> productList = productService.findAllProduct();

        for (Product product : productList) {
            double currentPrice = product.getPrice();
            for (Person person : personList) {
                List<Message> messageList = person.getMessageList();
                for (Message message : messageList) {
                    RequestDetails requestDetails = message.getRequestDetails();
                    if (requestDetails.getProduct() == product.getId()) {
                        double startPrice = requestDetails.getCurrentPrice();
                        if (currentPrice != startPrice) {
                            requestDetails.setStartPrice(startPrice);
                            requestDetails.setCurrentPrice(currentPrice);
                            requestDetailsService.saveRequestDetails(requestDetails);
                            String article = product.getArticle();
                            System.out.println(product.getProductName() + " (артикул: " + article + ") "
                                    + "изменение цены: " + startPrice + " -> " + currentPrice);
                            if (currentPrice != -1) {
                                String s = product.getProductName() + " (артикул: " + article + ") "
                                        + " \n" + "изменение цены: " + startPrice + " -> " + currentPrice
                                        + "\n" + "https://www.ozon.ru/product/"+article;
                                bot.sendText(person.getTgUserID(), s);
                            } else {
                                String s = product.getProductName() + " (артикул: " + article + ") "
                                        + "\n" + "товара нет в наличии"
                                        + "\n" + "https://www.ozon.ru/product/"+article;
                                bot.sendText(person.getTgUserID(), s);
                            }
                        }
                    }
                }
            }
        }
    }
}