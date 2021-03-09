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
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


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
            errorMessage += "<p>" + ERROR_NOTE_TITLE_REQUIRED + "</p>";
        }

        if (note.getNotedescription() == "") {
            errorMessage += "<p>" + ERROR_NOTE_DESCRIPTION_REQUIRED + "</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        // credits: https://knowledge.udacity.com/questions/354121
        User user = userService.getUser(authentication.getPrincipal().toString());

        this.noteService.addNote(note, user);

        attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_NOTE_CREATE + "</p>");
        return "redirect:/home";

    }


    @PostMapping("/edit/{id}")
    public String editNote(Authentication authentication, @PathVariable("id") int id, @ModelAttribute("formNote") Note note, Model model, RedirectAttributes attributes) {

        String errorMessage = "";
        if (note.getNotetitle() == "") {
            errorMessage += "<p>" + ERROR_NOTE_TITLE_REQUIRED + "</p>";
        }

        if (note.getNotedescription() == "") {
            errorMessage += "<p>" + ERROR_NOTE_DESCRIPTION_REQUIRED + "</p>";
        }

        if (errorMessage != "") {
            attributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.noteService.editNote(note, user, id)) {
            attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_NOTE_UPDATE + "</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_GENERAL + "</p>");
        }

        return "redirect:/home";

    }


    @GetMapping("/delete/{id}")
    public String deleteNote(Authentication authentication, @PathVariable("id") int id, RedirectAttributes attributes) {

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.noteService.deleteNote(id, (int)user.getUserId())) {
            attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_NOTE_DELETE + "</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_GENERAL + "</p>");
        }

        return "redirect:/home";

    }

}
