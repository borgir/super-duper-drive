package com.udacity.jwdnd.course1.cloudstorage.common;

import java.lang.reflect.Field;

public final class Message {

    public static final String SUCCESS_CREDENTIAL_CREATE = "Credential added successfully";

    public static final String SUCCESS_CREDENTIAL_UPDATE = "Credential updated successfully";

    public static final String SUCCESS_CREDENTIAL_DELETE = "Credential deleted successfully";

    public static final String SUCCESS_FILE_UPLOAD = "File uploaded successfully";

    public static final String SUCCESS_FILE_DELETE = "File deleted successfully";

    public static final String SUCCESS_NOTE_CREATE = "Note added successfully";

    public static final String SUCCESS_NOTE_UPDATE = "Note successfully updated";

    public static final String SUCCESS_NOTE_DELETE = "Note deleted successfully";

    public static final String SUCCESS_SIGNUP = "You successfully signed up!";

    public static final String ERROR_GENERAL = "There was an error. Please try again.";

    public static final String ERROR_CREDENTIAL_URL_REQUIRED = "Url field is required";

    public static final String ERROR_CREDENTIAL_USERNAME_REQUIRED = "Username field is required";

    public static final String ERROR_CREDENTIAL_PASSWORD_REQUIRED = "Password field is required";

    public static final String ERROR_CREDENTIAL_DUPLICATE = "This username/url already exists on database.";

    public static final String ERROR_FILE_REQUIRED = "You must select a file";

    public static final String ERROR_FILE_UPLOAD = "Could not upload the file";

    public static final String ERROR_FILE_UPLOAD_SIZE = "File size exceeds limit. The maximum file size is";

    public static final String ERROR_NOTE_TITLE_REQUIRED = "Note title field is required";

    public static final String ERROR_NOTE_DESCRIPTION_REQUIRED = "Note description field is required";

    public static final String ERROR_NOTE_DESCRIPTION_LENGTH = "Note description field can't exceed the 1000 characters";

    public static final String ERROR_NOTE_DUPLICATE = "This note already exists on database.";

    public static final String ERROR_SIGNUP_GENERAL = "There was an error signing you up. Please try again.";

    public static final String ERROR_SIGNUP_USERNAME = "The username already exists";

    public static final String ERROR_FILE_USER_EXISTS = "The user already has a file with the name";




    /**
     * The constructor is set as private so that the class can't be instantiated
     */
    private Message() {}




    /**
     * Gets the message based on the 'constant' and the given 'value' param.
     * This method will join a message kept on a constant with some extra information (value) like the filename.
     * @param constantName message constant name
     * @param value value to be added to the  message
     * @return the message
     */
    public static final String getMessage(String constantName, String value) throws IllegalAccessException {

        boolean constantChecked = false;

        String constantValue = null;

        Field[] fields = Message.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(constantName)) {
                constantChecked = true;
                constantValue = (String) f.get(null);
                break;
            }
        }

        if (!constantChecked) {
            return "Unknown message";
        }

        return constantValue + ": " + value;

    }

}
