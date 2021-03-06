package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;

    private UserService userService;


    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public String addCredential(Authentication authentication, @ModelAttribute("formCredential") Credential credential, RedirectAttributes attributes) {

        String errorMessage = "";
        if (credential.getUrl() == "") {
            errorMessage += "<p>Url field is required</p>";
        }

        if (credential.getUsername() == "") {
            errorMessage += "<p>Username field is required</p>";
        }

        if (credential.getPassword() == "") {
            errorMessage += "<p>Password field is required</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        User user = userService.getUser(authentication.getPrincipal().toString());

        this.credentialService.addCredential(credential, user);

        attributes.addFlashAttribute("successMessage", "<p>Credential added successfully</p>");
        return "redirect:/home";

    }


    @PostMapping("/edit/{id}")
    public String editCredential(Authentication authentication, @PathVariable("id") int id, @ModelAttribute("formCredential") Credential credential, Model model, RedirectAttributes attributes) {

        String errorMessage = "";
        if (credential.getUrl() == "") {
            errorMessage += "<p>Url field is required</p>";
        }

        if (credential.getUsername() == "") {
            errorMessage += "<p>Username field is required</p>";
        }

        if (credential.getPassword() == "") {
            errorMessage += "<p>Password field is required</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        User user = userService.getUser(authentication.getPrincipal().toString());
        credential.setCredentialid(id);
        if (this.credentialService.editCredential(credential, user)) {
            attributes.addFlashAttribute("successMessage", "<p>Credential successfully updated</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>There was an error</p>");
        }

        return "redirect:/home";

    }


    @GetMapping("/delete/{id}")
    public String deleteCredential(Authentication authentication, @PathVariable("id") int id, RedirectAttributes attributes) {

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.credentialService.deleteCredential(id, (int)user.getUserId())) {
            attributes.addFlashAttribute("successMessage", "<p>Credential deleted successfully</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>There was an error. Please try again.</p>");
        }

        return "redirect:/home";

    }

}
