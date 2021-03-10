package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


/**
 * References:
 * https://www.baeldung.com/spring-maxuploadsizeexceeded
 * https://knowledge.udacity.com/questions/377483
 */
@ControllerAdvice
public class ExceptionController   {

    private Environment env;




    /**
     *
     * @param env env is injected so that the application.properties file can be accesses
     */
    public ExceptionController (Environment env) {
        this.env = env;
    }




    /**
     * This method handles the MaxUploadSizeExceededException exception thrown when an uploaded file exceeds the maximum size
     * @param attributes used to send attributes the controller where this method will be redirected to
     * @return the controller where is method should be redirected to
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(RedirectAttributes attributes) {
        // gets the application.properties max file size value
        String maxFileSize = env.getProperty("spring.servlet.multipart.max-file-size");
        attributes.addFlashAttribute("errorMessage", Message.getMessage(ERROR_FILE_UPLOAD_SIZE, maxFileSize));
        return "redirect:/home";
    }

}