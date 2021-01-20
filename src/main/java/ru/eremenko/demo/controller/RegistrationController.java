package ru.eremenko.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.eremenko.demo.model.User;
import ru.eremenko.demo.repo.UserRepo;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            return "redirect:/exchange";
        }
        user.setActive(true);
       userRepo.save(user);

        return "redirect:/login";
    }
    @GetMapping("/")
    public String redirectAfterLogout(){
        return "redirect:/exchange";
    }
}
