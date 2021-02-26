package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
     *
     * @param note
     */
    public void addNote(Note note) {
        noteMapper.insertNote(note);
    }


    /**
     *
     * @param note
     * @return
     */
    public boolean editNote(Note note) {
        return noteMapper.updateNote(note);
    }


    /**
     *
     * @param userid
     * @return
     */
    public List<Note> getAllLoggedUserNotes(int userid) {
        return noteMapper.getAllNotes(userid);
    }


    /**
     *
     * @param id
     * @param userid
     * @return
     */
    public boolean deleteNote(int id, int userid) {
        return noteMapper.deleteNote(id, userid);
    }

}
