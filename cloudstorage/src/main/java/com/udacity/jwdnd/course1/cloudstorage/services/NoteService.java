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
        /*
        Note newNote = new Note();
        newNote.setNotedescription(note.getNotedescription());
        newNote.setNotetitle(note.getNotetitle());
        newNote.setUserid(note.getUserid());
        */

        noteMapper.insertNote(note);
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public boolean deleteNote(int id) {
        return noteMapper.deleteNote(id);
    }

}
