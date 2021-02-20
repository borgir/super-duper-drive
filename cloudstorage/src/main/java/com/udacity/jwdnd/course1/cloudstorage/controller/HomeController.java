package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    private NoteService noteService;
    private UserService userService;




    /**
     *
     * @param noteService
     * @param userService
     */
    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
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
    public String getHomePage(@ModelAttribute("formNote") Note note, Model model, @ModelAttribute("successMessage") String successMessage, @ModelAttribute("errorMessage") String errorMessage) {

        System.out.println("attribute: " + errorMessage);
        model.addAttribute("notes", this.noteService.getAllNotes());
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("errorMessage", errorMessage);

        System.out.println("successMessage: " + successMessage);

        return "home";

    }
    
}
