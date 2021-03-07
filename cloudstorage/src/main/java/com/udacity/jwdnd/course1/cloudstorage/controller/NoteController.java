package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    private UserService userService;


    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public String addNote(Authentication authentication, @ModelAttribute("formNote") Note note, RedirectAttributes attributes) {

        String errorMessage = "";
        if (note.getNotetitle() == "") {
            errorMessage += "<p>Note title field is required</p>";
        }

        if (note.getNotedescription() == "") {
            errorMessage += "<p>Note description field is required</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        // credits: https://knowledge.udacity.com/questions/354121
        User user = userService.getUser(authentication.getPrincipal().toString());

        this.noteService.addNote(note, user);

        attributes.addFlashAttribute("successMessage", "<p>Note added successfully</p>");
        return "redirect:/home";

    }


    @PostMapping("/edit/{id}")
    public String editNote(Authentication authentication, @PathVariable("id") int id, @ModelAttribute("formNote") Note note, Model model, RedirectAttributes attributes) {

        String errorMessage = "";
        if (note.getNotetitle() == "") {
            errorMessage += "<p>Note title field is required</p>";
        }

        if (note.getNotedescription() == "") {
            errorMessage += "<p>Note description field is required</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.noteService.editNote(note, user, id)) {
            attributes.addFlashAttribute("successMessage", "<p>Note successfully updated</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>There was an error</p>");
        }

        return "redirect:/home";

    }


    @GetMapping("/delete/{id}")
    public String deleteNote(Authentication authentication, @PathVariable("id") int id, RedirectAttributes attributes) {

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.noteService.deleteNote(id, (int)user.getUserId())) {
            attributes.addFlashAttribute("successMessage", "<p>Note deleted successfully</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>There was an error. Please try again.</p>");
        }

        return "redirect:/home";

    }

}
