package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.exception.DuplicateFileNamePerUserException;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    private UserService userService;


    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    @PostMapping("/add")
    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes attributes) {

        String fileName = fileService.getFilename(file);

        if (fileName == "") {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_FILE_REQUIRED + "</p>");
            return "redirect:/home";
        }

        try {
            fileService.storeFile(authentication, file);
            attributes.addFlashAttribute("successMessage", "<p>" + Message.getMessage(SUCCESS_FILE_UPLOAD, file.getOriginalFilename())  + "</p>");
        } catch (DuplicateFileNamePerUserException e) {
            attributes.addFlashAttribute("errorMessage", "<p>" + e.getMessage()  + "</p>");
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_FILE_UPLOAD + "</p>");
        }

        return "redirect:/home";

    }


    @GetMapping("/download/{id}")
    public HttpEntity<byte[]> downloadFile(@PathVariable("id") int id, RedirectAttributes attributes) {

        File file = this.fileService.downloadFile(id);

        byte[] fileContents  = file.getFiledata();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContenttype()));
        headers.add("content-disposition", "inline;filename=" + file.getFilename());
        headers.setContentDispositionFormData(file.getFilename(), file.getFilename());
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileContents, headers, HttpStatus.OK);
        return response;

    }


    @GetMapping("/delete/{id}")
    public String deleteFile(Authentication authentication, @PathVariable("id") int id, RedirectAttributes attributes) {

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.fileService.deleteFile(id, (int)user.getUserId())) {
            attributes.addFlashAttribute("successMessage", "<p>" + SUCCESS_FILE_DELETE + "</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>" + ERROR_GENERAL + "</p>");
        }

        return "redirect:/home";

    }

}
