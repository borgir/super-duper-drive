package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {




    /**
     * Will make the login page available whenever the user is not logged. Otherwise it will redirect the user to the homepage.
     * @param model will hold and make available on the frontend any message, like user created successfully.
     * @param successMessage will hold an eventual success message if the user comes from the signup page
     * @return login page view name OR the endpoint where the user will be redirected to when logged
     */
    @GetMapping()
    public String loginView(Model model, @ModelAttribute("successMessage") String successMessage) {

        model.addAttribute("successMessage", successMessage);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }

        return "redirect:/home";

    }

}
