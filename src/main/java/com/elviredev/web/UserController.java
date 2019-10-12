package com.elviredev.web;

import com.elviredev.dao.AppUserRepository;
import com.elviredev.entities.AppUser;
import com.elviredev.services.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/register")
    public AppUser register(@RequestBody UserForm userForm){ // données envoyées au format JSON
        return accountService.saveAppUser(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
    }
}

@Data
class UserForm {
    private String username;
    private String password;
    private String confirmedPassword;
}
