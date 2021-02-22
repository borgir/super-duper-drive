package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note) {
        noteMapper.insertNote(note);
    }

    public boolean editNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public List<Note> getAllLoggedUserNotes(int userid) {
        System.out.println("NoteService userid: " + userid);
        return noteMapper.getAllNotes(userid);
    }

    public boolean deleteNote(int id) {
        return noteMapper.deleteNote(id);
    }

}
