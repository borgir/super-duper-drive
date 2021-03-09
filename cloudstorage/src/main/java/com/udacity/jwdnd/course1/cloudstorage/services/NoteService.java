package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class NoteService {

    private NoteMapper noteMapper;




    /**
     *
     * @param noteMapper
     */
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }




    /**
     * Tries to add a new note to the current logged user
     * @param note the object containing the note's form data
     * @param user user object that will have it's ID extracted
     * @return the boolean result of the operation
     */
    public boolean addNote(Note note, User user) {
        Note newNote = new Note(note.getNotetitle(), note.getNotedescription(), (int)user.getUserId());
        return noteMapper.insertNote(newNote);
    }




    /**
     * Tries to update an existing note based on the given ID
     * @param note the object containing the note's form data
     * @param user user object that will have it's ID extracted
     * @param id note ID
     * @return the boolean result of the operation
     */
    public boolean editNote(Note note, User user, int id) {
        Note updatedNote = new Note(id, note.getNotetitle(), note.getNotedescription(), (int)user.getUserId());
        return noteMapper.updateNote(updatedNote);
    }




    /**
     * Gets a list of all the current logged user notes
     * @param userid
     * @return the list of notes
     */
    public List<Note> getAllLoggedUserNotes(int userid) {
        return noteMapper.getAllNotes(userid);
    }




    /**
     * Tries to delete a note based on the given note's ID and the current logged user ID
     * @param id note's ID
     * @param userid
     * @return boolean result of the operation
     */
    public boolean deleteNote(int id, int userid) {
        return noteMapper.deleteNote(id, userid);
    }

}
