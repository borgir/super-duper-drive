package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class NoteService {

    private NoteMapper noteMapper;


    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    public void addNote(Note note, User user) {
        Note newNote = new Note(note.getNotetitle(), note.getNotedescription(), (int)user.getUserId());
        noteMapper.insertNote(newNote);
    }


    public boolean editNote(Note note, User user, int id) {
        Note updatedNote = new Note(id, note.getNotetitle(), note.getNotedescription(), (int)user.getUserId());
        return noteMapper.updateNote(updatedNote);
    }


    public List<Note> getAllLoggedUserNotes(int userid) {
        return noteMapper.getAllNotes(userid);
    }


    public boolean deleteNote(int id, int userid) {
        return noteMapper.deleteNote(id, userid);
    }

}
