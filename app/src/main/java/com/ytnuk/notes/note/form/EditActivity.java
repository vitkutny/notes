package com.ytnuk.notes.note.form;

import android.os.Bundle;
import android.view.Menu;

import java.util.Arrays;

public class EditActivity extends FormActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		text.setText(note.getText());
		updateColor(note.getColor());
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		color.setSelection(Arrays.asList(colors).indexOf(note.getColor()));
		title.setText(note.getTitle());
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected boolean showDiscardChangesDialog() {
		return !text.getText().toString().trim().equals(note.getText());
	}
}
