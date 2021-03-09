package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class HomeController {

    private NoteService noteService;

    private FileService fileService;

    private CredentialService credentialService;

    private UserService userService;


    /**
     * Home constructor.
     * Will inject the note, file and credential services so that its data can be presented on the frontend
     * @param noteService
     * @param fileService
     * @param credentialService
     * @param userService
     */
    public HomeController(NoteService noteService, FileService fileService, CredentialService credentialService, UserService userService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userService = userService;
    }




    /**
     * Will make the home page available.
     * Will make use of the main services (note, file, credential) so that its data be shown on the frontend
     * @param authentication for user ID purposes
     * @param note will make available the note POJO to be used on the note's form
     * @param credential will make available the credential POJO to be used on the note's form
     * @param model will hold all the notes, files, credentials and messages (success or error)
     * @param successMessage holds the received success message
     * @param errorMessage holds the received error messages
     * @return the name of the view
     */
    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute("formNote") Note note, @ModelAttribute("formCredential") Credential credential, Model model, @ModelAttribute("successMessage") String successMessage, @ModelAttribute("errorMessage") String errorMessage) {

        User user = userService.getUser(authentication.getPrincipal().toString());
        model.addAttribute("notes", this.noteService.getAllLoggedUserNotes((int)user.getUserId()));
        model.addAttribute("files", this.fileService.getAllLoggedUserFiles((int)user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getAllLoggedUserCredentials((int)user.getUserId()));

        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);
        
        return "home";

    }
    
}
