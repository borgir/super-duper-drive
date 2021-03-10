package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.exception.DuplicateFileNamePerUserException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


@Service
public class FileService {

    private FileMapper fileMapper;

    private UserService userService;




    /**
     *
     * @param userService
     * @param fileMapper
     */
    public FileService(UserService userService, FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }




    /**
     * Tries to store a file on the database
     * @param authentication used to get the user ID
     * @param file the uploaded file
     * @return the boolean result of the operation
     * @throws IOException cause the get the file bytes method might fail
     * @throws DuplicateFileNamePerUserException signals when a file with the same name is already associated to the current logged user
     */
    public boolean storeFile(Authentication authentication, MultipartFile file) throws IOException, DuplicateFileNamePerUserException, IllegalAccessException{

        User user = userService.getUser(authentication.getPrincipal().toString());

        String fileName = getFilename(file);

        List<File> files = this.getFiles((int)user.getUserId(), fileName);
        if (files.size() > 0) {
            throw new DuplicateFileNamePerUserException(Message.getMessage("ERROR_FILE_USER_EXISTS", fileName));
        }

        String fileContentType = file.getContentType();
        byte[] fileBytes = file.getBytes();
        long fileSize = file.getSize();

        File newFile = new File();
        newFile.setFilename(fileName);
        newFile.setContenttype(fileContentType);
        newFile.setFilesize(fileSize);
        newFile.setUserid((int)user.getUserId());
        newFile.setFiledata(fileBytes);

        return fileMapper.insertFile(newFile);

    }




    /**
     * Gets all the current logged user files
     * @param userid
     * @return a list of files
     */
    public List<File> getAllLoggedUserFiles(int userid) {
        return fileMapper.getAllFiles(userid);
    }




    /**
     * Tries to delete a certain file based on the given file ID and the user ID
     * @param fileid
     * @param userid
     * @return boolean result of the operation
     */
    public boolean deleteFile(int fileid, int userid) {
        return fileMapper.deleteFile(fileid, userid);
    }




    /**
     * Gets the name of the file being uploaded
     * @param file
     * @return the name of the file
     */
    public String getFilename(MultipartFile file) {
        return StringUtils.cleanPath(file.getOriginalFilename());
    }




    /**
     * Gets a file object based on the file ID
     * @param id
     * @return a file object
     */
    public File downloadFile(int id) {
        return fileMapper.getFile(id);
    }




    /**
     * Gets a list of all the files a user matching the given filename.
     * This method is used to ensure that there isn't more than one file with the same name per user.
     * @param userid
     * @param filename name of the file
     * @return a list of files
     */
    public List<File> getFiles(int userid, String filename) {
        return this.fileMapper.getFiles(userid, filename);
    }

}
