package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String signupView() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "signup";
        }
        
        return "redirect:/home";

    }


    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes attributes) {

        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = ERROR_SIGNUP_USERNAME;
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = ERROR_SIGNUP_GENERAL;
            }
        }

        if (signupError == null) {
            attributes.addFlashAttribute("successMessage", SUCCESS_SIGNUP);
            return "redirect:/login";
        }

        model.addAttribute("errorMessage", signupError);
        return "signup";

    }

}
