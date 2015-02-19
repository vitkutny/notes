package com.ytnuk.notes.note.list;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ytnuk.notes.R;
import com.ytnuk.notes.note.Note;
import com.ytnuk.notes.note.NoteRepository;

import io.realm.RealmResults;

public class ListFragment extends Fragment {

	private GridView noteGrid;

	private NoteRepository noteRepository;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.note_list_list_fragment, container, false);
		noteGrid = (GridView) view.findViewById(R.id.note_list_list_fragment_list); //TODO: use dynamic height gridView
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		noteGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		noteGrid.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.note_list_list_action, menu);
				return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
					case R.id.note_delete:
						for (long id : noteGrid.getCheckedItemIds()) {
							noteRepository.remove(noteRepository.get(id));
						}
						Toast.makeText(getActivity(), R.string.note_deleted, Toast.LENGTH_LONG).show();
						mode.finish();
						return true;
					default:
						return false;
				}
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
			}
		});
	}

	public void setNotes(RealmResults<Note> notes) {
		noteGrid.setAdapter(new ListAdapter(getActivity(), notes, R.layout.note_list_list_adapter));
	}

	public void setNote(Note note) {
		noteGrid.smoothScrollToPosition(noteGrid.getAdapter().getCount() - 1 - (int) note.getId()); //TODO: why reversed?
	}

	public void setNoteSelectedListener(final NoteSelectedListener noteSelectedListener) {
		noteGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				noteSelectedListener.onNoteSelected((Note) noteGrid.getAdapter().getItem(position));
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		noteRepository = new NoteRepository(activity);
	}

	public interface NoteSelectedListener {

		public void onNoteSelected(Note note);
	}
}
