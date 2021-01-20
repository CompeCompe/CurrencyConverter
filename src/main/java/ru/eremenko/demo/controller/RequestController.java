package ru.eremenko.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;
import ru.eremenko.demo.model.Request;
import ru.eremenko.demo.model.User;
import ru.eremenko.demo.parser.XMLparser;
import ru.eremenko.demo.service.CurrencyService;
import ru.eremenko.demo.service.RequestService;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class RequestController {
    private final RequestService requestService;
    private final CurrencyService currencyService;
    private Map<String,Double> courses;

    @Autowired
    public RequestController(RequestService requestService, CurrencyService currencyService) throws ParserConfigurationException, SAXException, IOException {
        this.requestService = requestService;
        this.currencyService = currencyService;
        if(courses == null){
            currencyService.getCourses();
            courses = XMLparser.getCourses();
        }
    }

    @GetMapping("/exchange")
    public String exchange(Request request, Model model) {
        model.addAttribute("list", courses.keySet());
        return "exchange";
    }

    @PostMapping("/exchange")
    public String post(@AuthenticationPrincipal User user, Request request, Model model){
        model.addAttribute("result",conversation(request,user));
        model.addAttribute("list", XMLparser.getCourses().keySet());
        return "exchange";
    }

    @GetMapping("/stats")
    public String getHistory(Model model,Double amount, String currency){
        model.addAttribute("courses",courses.keySet());
        model.addAttribute("requests",requestService.findAll());
        return "history";
    }

    @PostMapping("/stats")
    public String statistic(@RequestParam Double amount,
                            @RequestParam String currency,
                            @RequestParam String kind,
                            Model model){

        List<Request> requests;
        Set<User> users = new HashSet<>();

        if (kind.equals("users")){
            requests = requestService.findAll().stream()
                    .filter(req -> filterByAmount(amount,currency,req))
                    .collect(Collectors.toList());
            for (Request req : requests){
                users.add(req.getUser());
            }
            model.addAttribute("users",users);
        }else{
            requests = requestService.findAll().stream()
                    .filter(req -> filterByAmount(amount,currency,req))
                    .collect(Collectors.toList());
            if (requests.size() == 0){
                requests = null;
            }
            model.addAttribute("requests",requests);
        }
        model.addAttribute("courses",courses.keySet());
        return "history";
    }


    private boolean filterByAmount(Double amount, String currency, Request request){

        double amountInCur = request.getAmount() * courses.get(request.getCurrencyFrom()) / courses.get(currency);

        return amountInCur > amount;
    }

    private double conversation(Request request, User user){
        String currencyFrom = request.getCurrencyFrom();
        String currencyTo = request.getCurrencyTo();
        double amountInRub = request.getAmount() * courses.get(currencyFrom);

        request.setUser(user);
        requestService.saveRequest(request);
        request.setAmount(null);
        return amountInRub / courses.get(currencyTo);
    }

}
