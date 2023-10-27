package com.example.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.database.NoteDatabase;
import com.example.notes.model.Note;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private NoteDatabase noteDatabase;
    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void remove(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().remove(note.getId());
            }
        });
        thread.start();
    }

    public void deleteAll() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().deleteAll();
            }
        });
        thread.start();
    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.notesDao().getNote();
    }

}
