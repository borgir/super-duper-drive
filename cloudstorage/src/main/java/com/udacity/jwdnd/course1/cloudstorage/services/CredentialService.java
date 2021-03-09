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




    /**
     * Injects the credential mapper and the encryption service.
     * @param credentialMapper will be used for CRUD operations
     * @param encryptionService
     */
    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }




    /**
     * Adds a new credential to the database
     * @param credential object containing the credential's form data
     * @param user needed to grab the user's ID
     * @return the boolean result of the operation
     */
    public boolean addCredential(Credential credential, User user) {

        String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);

        Credential newCredential = new Credential(credential.getUrl(), credential.getUsername(), key, encryptedPassword, (int)user.getUserId());
        return credentialMapper.insertCredential(newCredential);

    }

    /**
     * Updates an existing credential
     * @param credential contains the credential's form data
     * @param user this object will be used to get the user ID
     * @return the boolean result of the operation
     */
    public boolean editCredential(Credential credential, User user) {
        String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        Credential updatedCredential = new Credential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), key, encryptedPassword, (int)user.getUserId());
        return credentialMapper.updateCredential(updatedCredential);
    }




    /**
     * Gets all the credentials that belong to the current logged user
     * @param userid
     * @return
     */
    public List<Credential> getAllLoggedUserCredentials(int userid) {
        List<Credential> credentials = credentialMapper.getAllCredentials(userid);
        for (int i = 0; i < credentials.size(); i++) {
            Credential credential = credentials.get(i);
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credential.setRawPassword(decryptedPassword);
        }
        return credentials;
    }

    /**
     * Tries to deletes a credential based on the given credential ID and user ID
     * @param id
     * @param userid
     * @return the boolean result of the operation
     */
    public boolean deleteCredential(int id, int userid) {
        return credentialMapper.deleteCredential(id, userid);
    }




    /**
     * Gets a credential based on the given ID
     * @param id
     * @return
     */
    public Credential getCredential(int id) {
        return this.credentialMapper.getCredential(id);
    }

}
