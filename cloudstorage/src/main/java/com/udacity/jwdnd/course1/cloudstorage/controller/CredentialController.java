package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


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
            errorMessage += "<p>" + ERROR_CREDENTIAL_URL_REQUIRED + "</p>";
        }

        if (credential.getUsername() == "") {
            errorMessage += "<p>" + ERROR_CREDENTIAL_USERNAME_REQUIRED + "</p>";
        }

        if (credential.getPassword() == "") {
            errorMessage += "<p>" + ERROR_CREDENTIAL_PASSWORD_REQUIRED + "</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        User user = userService.getUser(authentication.getPrincipal().toString());

        this.credentialService.addCredential(credential, user);

        attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_CREDENTIAL_CREATE + "</p>");

        return "redirect:/home";

    }


    @PostMapping("/edit/{id}")
    public String editCredential(Authentication authentication, @PathVariable("id") int id, @ModelAttribute("formCredential") Credential credential, RedirectAttributes attributes) {

        String errorMessage = "";
        if (credential.getUrl() == "") {
            errorMessage += "<p>" + ERROR_CREDENTIAL_URL_REQUIRED + "</p>";
        }

        if (credential.getUsername() == "") {
            errorMessage += "<p>" + ERROR_CREDENTIAL_USERNAME_REQUIRED + "</p>";
        }

        if (credential.getPassword() == "") {
            errorMessage += "<p>" + ERROR_CREDENTIAL_PASSWORD_REQUIRED + "</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        User user = userService.getUser(authentication.getPrincipal().toString());
        credential.setCredentialid(id);
        if (this.credentialService.editCredential(credential, user)) {
            attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_CREDENTIAL_UPDATE + "</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_GENERAL + "</p>");
        }

        return "redirect:/home";

    }


    @GetMapping("/delete/{id}")
    public String deleteCredential(Authentication authentication, @PathVariable("id") int id, RedirectAttributes attributes) {

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.credentialService.deleteCredential(id, (int)user.getUserId())) {
            attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_CREDENTIAL_DELETE + "</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_GENERAL + "</p>");
        }

        return "redirect:/home";

    }

}
