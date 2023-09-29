package org.example.tg_bot_ozon.repository;

import org.example.tg_bot_ozon.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByArticle(String article);

}
