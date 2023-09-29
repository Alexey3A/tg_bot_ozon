package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Message;
import org.example.tg_bot_ozon.entity.Person;
import org.example.tg_bot_ozon.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    Product findByArticle(String article);
    Product saveProduct(Product product, Person person, Message message);
    Product saveProduct(Product product);
    void deleteProductFromPerson(Person person, String article);
    void deleteProduct(String article);
}
