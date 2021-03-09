package com.udacity.jwdnd.course1.cloudstorage.common;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

public final class Message {

    private Message() {}



    public static final String ERROR_GENERAL = "There was an error. Please try again.";

    // credential
    public static final String ERROR_CREDENTIAL_URL_REQUIRED = "Url field is required";

    public static final String ERROR_CREDENTIAL_USERNAME_REQUIRED = "Username field is required";

    public static final String ERROR_CREDENTIAL_PASSWORD_REQUIRED = "Password field is required";

    public static final String ERROR_FILE_REQUIRED = "You must select a file";

    public static final String ERROR_FILE_UPLOAD = "Could not upload the file";

    public static final String ERROR_NOTE_TITLE_REQUIRED = "Note title field is required";

    public static final String ERROR_NOTE_DESCRIPTION_REQUIRED = "Note description field is required";

    public static final String ERROR_SIGNUP_GENERAL = "There was an error signing you up. Please try again.";

    public static final String ERROR_SIGNUP_USERNAME = "The username already exists";

    public static final String ERROR_FILE_USER_EXISTS = "The user already has a file with the name";




    public static final String SUCCESS_CREDENTIAL_CREATE = "Credential added successfully";

    public static final String SUCCESS_CREDENTIAL_UPDATE = "Credential updated successfully";

    public static final String SUCCESS_CREDENTIAL_DELETE = "Credential deleted successfully";


    public static final String SUCCESS_FILE_UPLOAD = "File uploaded successfully";

    public static final String SUCCESS_FILE_DELETE = "File deleted successfully";


    public static final String SUCCESS_NOTE_CREATE = "Note added successfully";

    public static final String SUCCESS_NOTE_UPDATE = "Note successfully updated";

    public static final String SUCCESS_NOTE_DELETE = "Note deleted successfully";

    public static final String SUCCESS_SIGNUP = "You successfully signed up!";





    public static final String getMessage(String constant, String value) {
        return constant + ": " + value;
    }

}
