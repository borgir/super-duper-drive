package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(int noteid);

    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    boolean updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    boolean deleteNote(int noteid);

}
