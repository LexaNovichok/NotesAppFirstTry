package com.example.notes.model;



import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private int priority;
    private Boolean isDone;

    public Note(int id, String text, int priority, Boolean isDone) {
        this.id = id;
        this.text = text;
        this.priority = priority;
        this.isDone = isDone;
    }

    @Ignore
    public Note(String text, int priority) {
        this.text = text;
        this.priority = priority;
    }

    @Ignore
    public Note(String text, int priority, Boolean isDone) {
        this.text = text;
        this.priority = priority;
        this.isDone = isDone;
    }

    public int getId() { return id; }

    public String getText() { return text; }

    public int getPriority() { return priority; }

    public Boolean getIsDone() { return isDone; }

}
