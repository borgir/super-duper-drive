package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.udacity.jwdnd.course1.cloudstorage.common.Message.*;


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
     * @return an associative array with the type of result (success or error) and the related message
     */
    public Map<String, String> addNote(Note note, User user) {

        Map<String, String> map = new HashMap<String, String>();

        if (isDuplicate(note.getNotetitle(), note.getNotedescription(), (int)user.getUserId())) {
            map.put("errorMessage", "<p>" + ERROR_NOTE_DUPLICATE + "</p>");
            return map;
        }

        Note newNote = new Note(note.getNotetitle(), note.getNotedescription(), (int)user.getUserId());

        if (noteMapper.insertNote(newNote)) {
            map.put("successMessage", "<p>" + SUCCESS_NOTE_CREATE + "</p>");
        } else {
            map.put("errorMessage", "<p>" + ERROR_GENERAL + "</p>");
        }

        return map;

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




    /**
     * In order to avoid more than one Note with the same Title and Description from the same user, this methods checks precisely that.
     * @param title note title
     * @param description note description
     * @param userid the logged user ID
     * @return boolean result of the operation
     */
    public boolean isDuplicate(String title, String description, int userid) {
        List<Note> notes = noteMapper.getNotes(title, description, userid);

        System.out.println("notes: " + notes.size());

        if (notes.size() >= 1) {
            return true;
        }
        return false;
    }

}
