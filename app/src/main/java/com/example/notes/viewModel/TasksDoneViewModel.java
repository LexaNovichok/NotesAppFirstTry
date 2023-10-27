package com.example.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.notes.database.NoteDoneDatabase;
import com.example.notes.model.Note;

import java.util.List;

public class TasksDoneViewModel extends AndroidViewModel {

    private NoteDoneDatabase noteDoneDatabase;
    public TasksDoneViewModel(@NonNull Application application) {
        super(application);
        noteDoneDatabase = NoteDoneDatabase.getInstance(application);
    }

    public void remove(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDoneDatabase.notesDoneDao().remove(note.getId());
            }
        });
        thread.start();
    }

    public void deleteAll() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDoneDatabase.notesDoneDao().deleteAll();
            }
        });
        thread.start();
    }
    public void add(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDoneDatabase.notesDoneDao().add(note);
            }
        });
        thread.start();
    }

    public LiveData<List<Note>> getNotes() {
        return noteDoneDatabase.notesDoneDao().getNote();
    }
}
