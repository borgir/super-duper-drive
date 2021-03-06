package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Service
public class FileService {

    private FileMapper fileMapper;

    private UserService userService;


    public FileService(UserService userService, FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }


    public boolean storeFile(Authentication authentication, MultipartFile file) throws IOException {

        String fileName = getFilename(file);

        String fileContentType = file.getContentType();
        byte[] fileBytes = file.getBytes();
        long fileSize = file.getSize();

        User user = userService.getUser(authentication.getPrincipal().toString());

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

}
