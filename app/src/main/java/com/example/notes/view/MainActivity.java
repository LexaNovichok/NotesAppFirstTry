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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.notes.R;
import com.example.notes.adapter.NotesAdapter;
import com.example.notes.model.Note;
import com.example.notes.viewModel.MainViewModel;
import com.example.notes.viewModel.TasksDoneViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout goToTasksDoneButton;
    private RecyclerView rcView;
    private NotesAdapter notesAdapter;
    private FloatingActionButton addNoteButton;
    private MainViewModel viewModel;
    private TasksDoneViewModel tasksDoneViewModel;
    private ImageButton clearAllButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class); //в get передаем название класса вью модели, который хотим получить
        tasksDoneViewModel = new ViewModelProvider(this).get(TasksDoneViewModel.class);

        initViews();

        notesAdapter = new NotesAdapter();
        rcView.setAdapter(notesAdapter);

        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {

            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); //позиция элемента, по которому свайпнули
                Note note = notesAdapter.getNote().get(position);
                viewModel.remove(note);
            }

        });
        itemTouchHelper.attachToRecyclerView(rcView);

        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); //позиция элемента, по которому свайпнули
                Note note = notesAdapter.getNote().get(position);
                Note note1 = notesAdapter.getNote().get(position);

                viewModel.remove(note);
                tasksDoneViewModel.add(note1);
            }

        });
        itemTouchHelper2.attachToRecyclerView(rcView);


        goToTasksDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TasksDoneActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }
        });


        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }
        });


        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAll();
            }
        });


        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes); //добавление списка в адаптер (списка из бд)
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews() {
        goToTasksDoneButton = findViewById(R.id.task_done_button);
        rcView = findViewById(R.id.rcView);
        addNoteButton = findViewById(R.id.addNoteButton);
        clearAllButton = findViewById(R.id.clearAllButton);
    }


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

}