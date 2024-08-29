package com.example.notes_app;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private List<Note> notesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = getNotesFromDatabase();
        notesAdapter = new NotesAdapter(notesList);
        recyclerView.setAdapter(notesAdapter);

        view.findViewById(R.id.button_add_note).setOnClickListener(v -> addNote());

        view.findViewById(R.id.button_delete_note).setOnClickListener(v -> {
            List<Note> selectedNotes = notesAdapter.getSelectedNotes();
            if (!selectedNotes.isEmpty()) {
                showDeleteConfirmationDialog(selectedNotes);
            } else {
                Toast.makeText(getContext(), "Please select notes to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNote() {
        Note newNote = new Note("New Note", "");
        notesList.add(newNote);
        notesAdapter.notifyItemInserted(notesList.size() - 1);
        saveNoteToDatabase(newNote);
    }

    private void deleteSelectedNotes(List<Note> selectedNotes) {
        notesList.removeAll(selectedNotes);
        notesAdapter.notifyDataSetChanged();
        for (Note note : selectedNotes) {
            deleteNoteFromDatabase(note);
        }
        Toast.makeText(getContext(), "Deleted selected notes", Toast.LENGTH_SHORT).show();
    }

    private List<Note> getNotesFromDatabase() {
        return new ArrayList<>(); // Replace with actual database query
    }

    private void saveNoteToDatabase(Note note) {
        // Logic to save note to the database
    }

    private void deleteNoteFromDatabase(Note note) {
        // Logic to delete note from the database
    }

    private void showDeleteConfirmationDialog(List<Note> selectedNotes) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Notes")
                .setMessage("Are you sure you want to delete the selected notes?")
                .setPositiveButton("Yes", (dialog, which) -> deleteSelectedNotes(selectedNotes))
                .setNegativeButton("No", null)
                .show();
    }
}
