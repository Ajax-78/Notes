package com.example.notes_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final List<Note> notesList;
    private final List<Note> selectedNotes = new ArrayList<>();

    public NotesAdapter(List<Note> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.checkBox.setChecked(selectedNotes.contains(note));

        holder.itemView.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                selectedNotes.remove(note);
                holder.checkBox.setChecked(false);
            } else {
                selectedNotes.add(note);
                holder.checkBox.setChecked(true);
            }
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedNotes.contains(note)) {
                    selectedNotes.add(note);
                }
            } else {
                selectedNotes.remove(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public List<Note> getSelectedNotes() {
        return selectedNotes;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

       EditText titleTextView;

       EditText noteContent;
        CheckBox checkBox;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.edit_note_title);
            checkBox = itemView.findViewById(R.id.note_checkbox);
            noteContent=itemView.findViewById (R.id.edit_note_content);
        }
    }
}


