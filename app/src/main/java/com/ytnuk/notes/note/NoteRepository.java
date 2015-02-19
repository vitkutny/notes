package com.ytnuk.notes.note;

import android.content.Context;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

//TODO: google sync backup
public class NoteRepository {

	private Realm database;

	public NoteRepository(Context context) {
		database = Realm.getInstance(context);
	}

	public Note get(long id) {
		return id < database.allObjects(Note.class).size() ? database.allObjects(Note.class).get((int) id) : null;
	}

	public RealmResults<Note> find(String search) {
		RealmQuery<Note> query = database.where(Note.class);
		if (search != null) {
			query.contains("text", search);
		}
		RealmResults<Note> notes = query.findAll();
		notes.sort("created", false);
		return notes;
	}

	public RealmResults<Note> find() {
		return find(null);
	}

	public Note save(Note note, Note data) {
		database.beginTransaction();
		if (note == null) {
			note = database.createObject(Note.class);
			note.setCreated(new Date());
		} else {
			note.setEdited(new Date());
		}
		populate(note, data);
		database.commitTransaction();
		return note;
	}

	public void populate(Note note, Note data) {
		note.setTitle(data.getTitle());
		note.setText(data.getText());
		note.setColor(data.getColor());
	}

	public void remove(Note note) {
		database.beginTransaction();
		note.removeFromRealm();
		database.commitTransaction();
	}
}
