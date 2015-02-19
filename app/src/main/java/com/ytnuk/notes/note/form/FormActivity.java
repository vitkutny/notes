package com.ytnuk.notes.note.form;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ytnuk.notes.R;
import com.ytnuk.notes.note.Note;
import com.ytnuk.notes.note.NoteActivity;
import com.ytnuk.notes.note.list.ListActivity;

abstract public class FormActivity extends NoteActivity {

	protected EditText title;

	protected EditText text;

	protected Spinner color;

	protected Integer[] colors;

	private AlertDialog discardChanges;

	private ColorDrawable actionBarBackground;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_form_form_activity);
		text = (EditText) findViewById(R.id.note_form_note_fragment_text);
		int[] colorsArray = getResources().getIntArray(R.array.note_colors);
		colors = new Integer[colorsArray.length];
		for (int i = 0; i < colorsArray.length; i++) {
			colors[i] = colorsArray[i];
		}
		if (getActionBar() != null) {
			getActionBar().setBackgroundDrawable(actionBarBackground = new ColorDrawable());
			getActionBar().setDisplayShowTitleEnabled(false);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		title.setHint(getTitle());
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.note_form_form_activity, menu);
		MenuItem colorMenuItem = menu.findItem(R.id.note_color);
		color = (Spinner) colorMenuItem.getActionView();
		color.setAdapter(new ColorAdapter(this, colors));
		color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				updateColor((int) color.getAdapter().getItem(position)); //TODO: updating actionBar color bug
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		MenuItem titleMenuItem = menu.findItem(R.id.note_title);
		titleMenuItem.expandActionView(); //TODO: crash while rotating with expanded title on API 15
		titleMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				if (showDiscardChangesDialog()) {
					discardChanges.show();
				} else {
					finish();
				}
				return false;
			}
		});
		title = (EditText) titleMenuItem.getActionView();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.note_changed);
		builder.setMessage(R.string.discard_changes);
		builder.setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		discardChanges = builder.create();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.note_save:
				if (text.length() == 0) {
					text.setError(getResources().getString(R.string.note_form_note_fragment_text_required));
					return false;
				}
				Note note = new Note();
				note.setTitle(title.getText().toString());
				note.setText(text.getText().toString().trim());
				note.setColor((Integer) color.getSelectedItem());
				note = noteRepository.save(this.note, note);
				Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
				Intent listIntent = new Intent(this, ListActivity.class);
				listIntent.putExtra(Note.class.toString(), note.getId());
				startActivity(listIntent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	abstract protected boolean showDiscardChangesDialog();

	protected void updateColor(int color) {
		getWindow().getDecorView().setBackgroundColor(color);
		actionBarBackground.setColor(color);
	}
}
