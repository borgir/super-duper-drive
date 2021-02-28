package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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
    private UserService userService;




    /**
     *
     * @param noteService
     * @param userService
     */
    public HomeController(NoteService noteService, FileService fileService, UserService userService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
    }




    /**
     *
     * @param note
     * @param model
     * @param successMessage
     * @param errorMessage
     * @return
     */
    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute("formNote") Note note, @ModelAttribute("formCredential") Credential credential, Model model, @ModelAttribute("successMessage") String successMessage, @ModelAttribute("errorMessage") String errorMessage) {

        User user = userService.getUser(authentication.getPrincipal().toString());
        model.addAttribute("notes", this.noteService.getAllLoggedUserNotes((int)user.getUserId()));
        model.addAttribute("files", this.fileService.getAllLoggedUserFiles((int)user.getUserId()));


        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);
        
        return "home";

    }
    
}
