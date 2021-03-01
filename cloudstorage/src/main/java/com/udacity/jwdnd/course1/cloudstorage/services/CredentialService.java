package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {


    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }



    public void addCredential(Credential credential, User user) {

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encryptionService.generateKey());
        Credential newCredential = new Credential(credential.getUrl(), credential.getUsername(), encryptionService.generateKey(), encryptedPassword, (int)user.getUserId());
        credentialMapper.insertCredential(newCredential);

    }


    public List<Credential> getAllLoggedUserCredentials(int userid) {
        return credentialMapper.getAllCredentials(userid);
    }

    public boolean deleteCredential(int id, int userid) {
        return credentialMapper.deleteCredential(id, userid);
    }

}
