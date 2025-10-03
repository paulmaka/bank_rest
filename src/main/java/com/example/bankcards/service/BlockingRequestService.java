package com.example.bankcards.service;

import com.example.bankcards.entity.BlockingRequest;
import com.example.bankcards.repository.BlockingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockingRequestService {

    private final BlockingRequestRepository blockingRequestRepository;

    @Autowired
    public BlockingRequestService(BlockingRequestRepository blockingRequestRepository) {
        this.blockingRequestRepository = blockingRequestRepository;
    }

    public BlockingRequest save(BlockingRequest blockingRequest) {
        return blockingRequestRepository.save(blockingRequest);
    }

}
