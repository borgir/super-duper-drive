package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFile(int fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    List<File> getFiles(int userid, String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(int userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    boolean insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid} AND userid = #{userid}")
    boolean deleteFile(int fileid, int userid);

}
