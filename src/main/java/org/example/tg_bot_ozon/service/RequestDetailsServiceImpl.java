package org.example.tg_bot_ozon.service;

import org.example.tg_bot_ozon.entity.RequestDetails;
import org.example.tg_bot_ozon.repository.RequestDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestDetailsServiceImpl implements RequestDetailsService{
    private final RequestDetailsRepository requestDetailsRepository;

    public RequestDetailsServiceImpl(RequestDetailsRepository requestDetailsRepository) {
        this.requestDetailsRepository = requestDetailsRepository;
    }

    @Override
    @Transactional
    public RequestDetails saveRequestDetails(RequestDetails requestDetails) {
        return requestDetailsRepository.save(requestDetails);
    }
}
