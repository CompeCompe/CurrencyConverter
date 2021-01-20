package ru.eremenko.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eremenko.demo.model.Request;
import ru.eremenko.demo.repo.RequestRepo;

import java.util.List;

@Service
public class RequestService {
    private final RequestRepo requestRepo;

    @Autowired
    public RequestService(RequestRepo requestRepo){
        this.requestRepo = requestRepo;
    }

    public List<Request> findAll() { return requestRepo.findAll();}

    public Request saveRequest(Request request){ return requestRepo.save(request);}

}
