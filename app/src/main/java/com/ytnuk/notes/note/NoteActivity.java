package com.ytnuk.notes.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

abstract public class NoteActivity extends Activity {

	protected Note note;

	protected NoteRepository noteRepository;

	private long id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteRepository = new NoteRepository(this);
		if (getIntent().hasExtra(Note.class.toString())) {
			note = noteRepository.get(id = getIntent().getLongExtra(Note.class.toString(), 0));
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		note = null;
		id = -1;
		if (intent.hasExtra(Note.class.toString())) {
			note = noteRepository.get(id = intent.getLongExtra(Note.class.toString(), 0));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (id >= 0 && (note = noteRepository.get(id)) == null) {
			finish();
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		note = noteRepository.get(id = savedInstanceState.getLong(Note.class.toString()));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (note != null) {
			outState.putLong(Note.class.toString(), note.getId());
		}
	}

	@Override
	public Intent getParentActivityIntent() {
		Intent intent = super.getParentActivityIntent();
		if (note != null && intent != null && !intent.hasExtra(Note.class.toString())) {
			intent.putExtra(Note.class.toString(), note.getId());
		}
		return intent;
	}
}
