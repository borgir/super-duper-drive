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


    public FileService(UserService userService, FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }


    public boolean storeFile(Authentication authentication, MultipartFile file) throws IOException, DuplicateFileNamePerUserException {

        User user = userService.getUser(authentication.getPrincipal().toString());

        String fileName = getFilename(file);

        List<File> files = this.getFiles((int)user.getUserId(), fileName);
        if (files.size() > 0) {
            throw new DuplicateFileNamePerUserException(Message.getMessage(ERROR_FILE_USER_EXISTS, fileName));
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


    public List<File> getAllLoggedUserFiles(int userid) {
        return fileMapper.getAllFiles(userid);
    }


    public boolean deleteFile(int fileid, int userid) {
        return fileMapper.deleteFile(fileid, userid);
    }


    public String getFilename(MultipartFile file) {
        return StringUtils.cleanPath(file.getOriginalFilename());
    }


    public File downloadFile(int id) {
        return fileMapper.getFile(id);
    }

    public List<File> getFiles(int userid, String filename) {
        return this.fileMapper.getFiles(userid, filename);
    }

}
