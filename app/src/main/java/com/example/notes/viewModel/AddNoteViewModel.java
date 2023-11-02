package com.example.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notes.dao.NotesDao;
import com.example.notes.database.NoteDatabase;
import com.example.notes.model.Note;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void saveNote(Note note) {
        Disposable disposable = addNoteRx(note)
                //.delay(1, TimeUnit.SECONDS) //задержка перед тем как выполнятся дальнейшие операторы
                .subscribeOn(Schedulers.io()) //чтобы действие выполнялось в фоновом потоке
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() { //чтобы отреагировать на событие завершения операции у объекта completable
            @Override
            public void run() throws Throwable {
                shouldCloseScreen.postValue(true);
            }
        });
        compositeDisposable.add(disposable);
    }

    private Completable addNoteRx(Note note) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                notesDao.add(note);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
