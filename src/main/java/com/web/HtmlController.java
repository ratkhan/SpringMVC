package com.web;

import com.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class HtmlController {

    @GetMapping("/")
    public String homepage(Model model){
        model.addAttribute("username", "John");
        model.addAttribute("currentDate",new Date());
        model.addAttribute("registrationForm", new RegistrationForm());
        return "index.html";
    }

    @PostMapping("/")
    public String homepage(@ModelAttribute RegistrationForm registrationForm){
        System.out.println("User with a name " + registrationForm.getFirstName()
            + " tried to register ");
        return "index.html";
    }
}
