package com.example.notes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.model.Note;

import java.util.List;

@Dao
public interface NotesDoneDao {
    @Query("SELECT *FROM notes")
    LiveData<List<Note>> getNote();

    @Insert
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);

    @Query("DELETE FROM notes")
    void deleteAll();
}
