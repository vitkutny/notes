package com.ytnuk.notes.note.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytnuk.notes.R;
import com.ytnuk.notes.note.Note;
import com.ytnuk.notes.note.NoteRepository;

public class ViewFragment extends Fragment {

	private Note note;

	private NoteRepository noteRepository;

	public static ViewFragment newInstance(long id) {
		ViewFragment viewFragment = new ViewFragment();
		Bundle arguments = new Bundle();
		arguments.putLong(Note.class.toString(), id);
		viewFragment.setArguments(arguments);
		return viewFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.note_view_view_fragment, container, false);
		TextView text = (TextView) view.findViewById(R.id.note_view_view_fragment_text);
		text.setTextIsSelectable(true);
		text.setText(note.getText());
		view.setBackgroundColor(note.getColor());
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		note = noteRepository.get(getArguments().getLong(Note.class.toString()));
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		noteRepository = new NoteRepository(activity);
	}
}
