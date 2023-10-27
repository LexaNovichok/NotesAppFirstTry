package com.example.notes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.notes.R;
import com.example.notes.adapter.NotesAdapter;
import com.example.notes.model.Note;
import com.example.notes.viewModel.TasksDoneViewModel;

import java.util.ArrayList;
import java.util.List;

public class TasksDoneActivity extends AppCompatActivity {

    private LinearLayout goToTasksToDoButton;
    private TasksDoneViewModel tasksDoneViewModel;
    private NotesAdapter notesAdapter;
    private RecyclerView rcViewDone;
    private ImageButton clearAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_done);

        tasksDoneViewModel = new ViewModelProvider(this).get(TasksDoneViewModel.class);

        initViews();

        notesAdapter = new NotesAdapter();
        rcViewDone.setAdapter(notesAdapter);

        List<Note> notes = new ArrayList<>();
        notes.add(new Note("123", 2));
        notes.add(new Note("124", 1));
        notesAdapter.setNotes(notes);

        goToTasksToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksDoneViewModel.deleteAll();
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNote().get(position);

                tasksDoneViewModel.remove(note);
            }
        });
        itemTouchHelper.attachToRecyclerView(rcViewDone);



        //Подписываемся на изменение бд NoteDoneDatabase
        tasksDoneViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });
    }


    private void initViews() {
        goToTasksToDoButton = findViewById(R.id.tasks_need_to_do_button);
        rcViewDone = findViewById(R.id.rcViewDone);
        clearAllButton = findViewById(R.id.clearAllButton);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TasksDoneActivity.class);
        return intent;
    }
}