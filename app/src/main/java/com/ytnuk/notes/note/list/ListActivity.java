package com.ytnuk.notes.note.list;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.ytnuk.notes.R;
import com.ytnuk.notes.note.Note;
import com.ytnuk.notes.note.NoteActivity;
import com.ytnuk.notes.note.form.AddActivity;
import com.ytnuk.notes.note.view.ViewActivity;

//TODO: drawer always opened on large screens, not overlapping content... sidebar list as a fragment to make different layout without drawer and only with that list (or maybe using setDrawerLockMode?)
public class ListActivity extends NoteActivity implements ListFragment.NoteSelectedListener {

	private ListFragment listFragment;

	private DrawerLayout drawerLayout;

	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list_list_activity);
		if (getActionBar() != null) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		listFragment = (ListFragment) getFragmentManager().findFragmentByTag(ListFragment.class.toString());
		listFragment.setNoteSelectedListener(this);
		listFragment.setNotes(noteRepository.find());
		drawerLayout = (DrawerLayout) findViewById(R.id.note_list_list_activity);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.note, R.string.note);
		drawerLayout.setDrawerListener(drawerToggle);
		ListView listDrawer = (ListView) findViewById(R.id.note_list_list_activity_list_drawer);
		listDrawer.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"Prvni", "Druha"}));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			listFragment.setNotes(noteRepository.find(intent.getStringExtra(SearchManager.QUERY)));
		}
		if (note != null) {
			listFragment.setNote(note);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { //TODO: search should suggest note titles
		getMenuInflater().inflate(R.menu.note_list_list_activity, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.note_search);
		searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				listFragment.setNotes(noteRepository.find());
				return true;
			}
		});
		SearchView searchView = (SearchView) searchMenuItem.getActionView();
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.note_add:
				startActivity(new Intent(this, AddActivity.class));
				return true;
			default:
				return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	public void onNoteSelected(Note note) {
		Intent viewNote = new Intent(this, ViewActivity.class);
		viewNote.putExtra(Note.class.toString(), note.getId());
		startActivity(viewNote);
	}
}
