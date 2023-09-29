package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.Message;
import org.example.tg_bot_ozon.entity.Person;
import org.example.tg_bot_ozon.entity.Product;
import org.example.tg_bot_ozon.entity.RequestDetails;
import org.example.tg_bot_ozon.exception.ProductAbsenceException;
import org.example.tg_bot_ozon.repository.MessageRepository;
import org.example.tg_bot_ozon.repository.PersonRepository;
import org.example.tg_bot_ozon.repository.ProductRepository;
import org.example.tg_bot_ozon.repository.RequestDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final RequestDetailsRepository requestDetailsRepository;
    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository
            , RequestDetailsRepository requestDetailsRepository
            , PersonRepository personRepository
            , MessageRepository messageRepository) {
        this.productRepository = productRepository;
        this.requestDetailsRepository = requestDetailsRepository;
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product findByArticle(String article){
        return productRepository.findByArticle(article);
    }

    @Override
    @Transactional
    public Product saveProduct(Product product, Person person, Message message) {
        product.addPersonToProduct(person);
        product = productRepository.save(product);
        RequestDetails requestDetails = new RequestDetails();
        requestDetails.setProduct(product.getId());
        requestDetails.setCurrentPrice(product.getPrice());
        requestDetails = requestDetailsRepository.save(requestDetails);
        message.setPerson(person);
        message.setRequestDetails(requestDetails);
        message = messageRepository.save(message);
        return product;
    }

    @Override
    @Transactional
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductFromPerson(Person person, String article) {
        Product pp = productRepository.findByArticle(article);
        if (pp != null) {
            Set<Product> personProducts = person.getProductList();
            Set<Product> newPersonProducts = new HashSet<>();
            int i = 0;
            for (Product personProduct : personProducts) {
                if (!personProduct.getArticle().equals(article)) {
                    newPersonProducts.add(personProduct);
                } else {
                    ++i;
                }
            }
            if (i != 0) {
                person.setProductList(newPersonProducts);
                personRepository.save(person);
                List<Message> messageList = messageRepository.findAllByMessageTextAndPerson(article, person);
                List<RequestDetails> requestDetailsList = new ArrayList<>();
                for(Message message : messageList){
                    messageRepository.delete(message);
                    requestDetailsList.add(message.getRequestDetails());
                }
                for(RequestDetails requestDetails : requestDetailsList) {
                    requestDetailsRepository.delete(requestDetails);
                }
            } else throw new ProductAbsenceException();
        } else throw new ProductAbsenceException();

        pp = productRepository.findByArticle(article);
        if (pp.getPersonList().isEmpty()) {
            productRepository.delete(pp);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(String article) {
        Product pp = productRepository.findByArticle(article);
        if (pp.getPersonList().isEmpty()) {
            productRepository.delete(pp);
        }
    }
}
