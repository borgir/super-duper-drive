package com.udacity.jwdnd.course1.cloudstorage.controller;

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
     */
    @PostMapping("/add")
    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, RedirectAttributes attributes) {

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


    /**
     *
     * @param authentication
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteFile(Authentication authentication, @PathVariable("id") int id, RedirectAttributes attributes) {

        User user = userService.getUser(authentication.getPrincipal().toString());

        if (this.fileService.deleteFile(id, (int)user.getUserId())) {
            attributes.addFlashAttribute("successMessage", "<p>File deleted successfully</p>");
        } else {
            attributes.addFlashAttribute("errorMessage", "<p>There was an error. Please try again.</p>");
        }

        return "redirect:/home";

    }

}
