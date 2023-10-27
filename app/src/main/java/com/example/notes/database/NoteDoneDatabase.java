package com.example.notes.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes.dao.NotesDao;
import com.example.notes.dao.NotesDoneDao;
import com.example.notes.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDoneDatabase extends RoomDatabase {

    private static NoteDoneDatabase instance = null;
    private static final String DB_NAME = "notesDone.db";

    public static NoteDoneDatabase getInstance(Application application) { //Возвращает экземпляр бд
        if (instance == null) {
            instance = Room.databaseBuilder( //получаем экземпляр бд, нарпямую не можем создать экземпляр тк класс абстрактный
                    application, //передаем application в качестве контекста
                    NoteDoneDatabase.class, //класс базы данных
                    DB_NAME //имя базы данных
            ).allowMainThreadQueries().build();
            //databaseBuilder создаст наследника класса NoteDatabase и вернет его экземпляр
        }
        return instance;
    }

    public abstract NotesDoneDao notesDoneDao();

}
