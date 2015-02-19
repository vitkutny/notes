package com.ytnuk.notes.note.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ytnuk.notes.R;
import com.ytnuk.notes.note.Note;
import com.ytnuk.notes.note.NoteActivity;
import com.ytnuk.notes.note.form.EditActivity;
import com.ytnuk.notes.note.list.ListActivity;

import io.realm.RealmResults;

public class ViewActivity extends NoteActivity {

	private RealmResults<Note> notes;

	private AlertDialog deleteDialog;

	private ColorDrawable actionBarBackground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_view_view_activity);
		if (getActionBar() != null) {
			getActionBar().setBackgroundDrawable(actionBarBackground = new ColorDrawable());
		}
		updateActionBar(note);
		notes = noteRepository.find();
		ViewPager pager = (ViewPager) findViewById(R.id.note_view_view_activity_view_fragment);
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				updateActionBar(note = notes.get(position));
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		pager.setAdapter(new PageAdapter(getFragmentManager(), notes));
		pager.setCurrentItem(notes.indexOf(note));
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.note_delete_confirm);
		builder.setMessage(R.string.note_delete_confirm_message);
		builder.setPositiveButton(R.string.note_delete, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				noteRepository.remove(note);
				Toast.makeText(ViewActivity.this, R.string.note_deleted, Toast.LENGTH_LONG).show();
				startActivity(new Intent(ViewActivity.this, ListActivity.class));
				finish();
			}
		});
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		deleteDialog = builder.create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.note_view_view_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.note_edit:
				Intent editNote = new Intent(this, EditActivity.class);
				editNote.putExtra(Note.class.toString(), note.getId());
				startActivity(editNote);
				return true;
			case R.id.note_share:
				Intent share = new Intent();
				share.setAction(Intent.ACTION_SEND);
				share.setType("text/plain");
				if (note.getTitle().length() > 0) {
					share.putExtra(Intent.EXTRA_TITLE, note.getTitle());
					share.putExtra(Intent.EXTRA_SUBJECT, note.getTitle());
				}
				share.putExtra(Intent.EXTRA_TEXT, note.getText());
				startActivity(Intent.createChooser(share, getString(R.string.note_share)));
				return true;
			case R.id.note_delete:
				deleteDialog.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void updateActionBar(Note note) {
		if (getActionBar() != null) {
			actionBarBackground.setColor(note.getColor());
			if (note.getTitle().length() > 0) {
				getActionBar().setTitle(note.getTitle());
			} else {
				getActionBar().setTitle(getTitle());
			}
		}
	}
}
