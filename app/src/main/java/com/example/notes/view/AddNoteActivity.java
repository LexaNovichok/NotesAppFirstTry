package com.example.notes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.viewModel.AddNoteViewModel;
import com.example.notes.viewModel.MainViewModel;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;
    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
       // viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class); //в get передаем название класса вью модели, который хотим получить
        initViews();

        radioButtonLow.setChecked(true);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) { finish(); }
            }
        });
    }


    private void saveNote() {
        String text = editTextNote.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(AddNoteActivity.this, "Enter some text", Toast.LENGTH_SHORT).show();
        } else {
            int priority = getPriority();

            Note note = new Note(text, priority, false);
            viewModel.saveNote(note);
        }
    }

    private int getPriority() {
        int priority;
        if (radioButtonLow.isChecked()) { priority = 0; }
        else if (radioButtonMedium.isChecked()) { priority = 1; }
        else { priority = 2; }

        return priority;
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        buttonSave = findViewById(R.id.buttonSave);
    }
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        return intent;
    }



}