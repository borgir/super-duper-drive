package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }



    public boolean addCredential(Credential credential, User user) {

        String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);

        Credential newCredential = new Credential(credential.getUrl(), credential.getUsername(), key, encryptedPassword, (int)user.getUserId());
        return credentialMapper.insertCredential(newCredential);

    }

    public boolean editCredential(Credential credential, User user) {
        String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        Credential updatedCredential = new Credential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), key, encryptedPassword, (int)user.getUserId());
        return credentialMapper.updateCredential(updatedCredential);
    }

    public List<Credential> getAllLoggedUserCredentials(int userid) {
        List<Credential> credentials = credentialMapper.getAllCredentials(userid);
        for (int i = 0; i < credentials.size(); i++) {
            Credential credential = credentials.get(i);
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setRawPassword(decryptedPassword);
        }
        return credentials;
    }

    public boolean deleteCredential(int id, int userid) {
        return credentialMapper.deleteCredential(id, userid);
    }

    public Credential getCredential(int id) {
        return this.credentialMapper.getCredential(id);
    }

}
