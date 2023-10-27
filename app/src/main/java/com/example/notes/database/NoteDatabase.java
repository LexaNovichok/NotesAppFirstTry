package com.example.notes.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.notes.dao.NotesDao;
import com.example.notes.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase { //класс отвечает за создание и настройку бд

    private static NoteDatabase instance = null;
    private static final String DB_NAME = "notes.db";

    public static NoteDatabase getInstance(Application application) { //возвращаем экземпляр бд
        // (не можем просто вернуть тк класс абстрактный)
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    NoteDatabase.class,
                    DB_NAME
            ).allowMainThreadQueries().build();
            //databaseBuilder создаст наследника класса NoteDatabase и вернет его экземпляр
        }
        return instance;
    }

    public abstract NotesDao notesDao(); //room сгенерирует реализацию интерфейса NotesDao
}
