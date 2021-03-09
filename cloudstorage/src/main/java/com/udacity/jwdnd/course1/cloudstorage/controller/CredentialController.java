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




    /**
     * Handles the Credential Controller initialization.
     * @param credentialService will be used to credential business login
     * @param userService will be used to extract the logged user ID
     */
    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }




    /**
     * This method will handle Add Credential post request.
     * Will check if all the required fields have been set and if so, it will try to add them by requesting the proper method on the credential serice class
     * @param authentication to get the user ID
     * @param credential contains the <form> submited data
     * @param attributes will hold the attributes that will be sent to the home controller
     * @return endpoint this controller is redirected to
     */
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




    /**
     * Handles the credential edit post request.
     * @param authentication to get the user ID
     * @param id the credential ID present in the URL
     * @param credential the form fields
     * @param attributes will hold the attributes that will be sent to the home controller
     * @return endpoint this controller is redirected to
     */
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




    /**
     * Handles the credential deletion request.
     * @param authentication for getting the user ID
     * @param id credential ID
     * @param attributes will hold the attributes that will be sent to the home controller
     * @return endpoint this controller is redirected to
     */
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
