package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping("/file")
public class FileController {


    private FileService fileService;
    private UserService userService;

    /**
     *
     * @param userService
     * @param fileService
     */
    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    /**
     *
     * @param authentication
     * @param file
     * @param attributes
     * @return
     * @throws IOException
     */
    @PostMapping("/add")
    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes attributes) throws IOException {

        String fileName = fileService.getFilename(file);

        if (fileName == "") {
            attributes.addFlashAttribute("errorMessage", "<p>You must select a file</p>");
            return "redirect:/home";
        }


        try {
            fileService.storeFile(authentication, file);
            attributes.addFlashAttribute("successMessage", "<p>Uploaded the file successfully: "  + file.getOriginalFilename() + "</p>");
        } catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", "<p>Could not upload the file</p>");
        }

        return "redirect:/home";

    }


    /**
     *
     * @param id
     * @param attributes
     * @return
     */
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

}
