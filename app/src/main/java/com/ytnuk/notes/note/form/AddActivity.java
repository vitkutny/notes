package com.ytnuk.notes.note.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

//TODO: sharing to this activity title/text or using attachments image/video/audio
public class AddActivity extends FormActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getType() != null && getIntent().getType().equals("text/plain") && getIntent().hasExtra(Intent.EXTRA_TEXT)) {
			text.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
		}
		updateColor(colors[0]);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (getIntent().getType() != null && getIntent().getType().equals("text/plain")) {
			if (getIntent().hasExtra(Intent.EXTRA_TITLE)) {
				title.setText(getIntent().getStringExtra(Intent.EXTRA_TITLE));
			} else if (getIntent().hasExtra(Intent.EXTRA_SUBJECT)) {
				title.setText(getIntent().getStringExtra(Intent.EXTRA_TITLE));
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected boolean showDiscardChangesDialog() {
		return text.getText().toString().trim().length() > 0;
	}
}
