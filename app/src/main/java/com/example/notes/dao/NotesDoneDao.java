package com.example.notes.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes.model.Note;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDoneDao {
    @Query("SELECT *FROM notes")
    Single<List<Note>> getNote();

    @Insert
    Completable add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    Completable remove(int id);

    @Query("DELETE FROM notes")
    Completable deleteAll();
}
