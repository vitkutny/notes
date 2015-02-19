package com.ytnuk.notes.note.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytnuk.notes.R;
import com.ytnuk.notes.note.Note;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class ListAdapter extends RealmBaseAdapter<Note> {

	private int resource;

	private ViewHolder viewHolder;

	public ListAdapter(Context context, RealmResults<Note> notes, int resource) {
		super(context, notes, true);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resource, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.note_view_view_fragment_title);
			viewHolder.text = (TextView) convertView.findViewById(R.id.note_view_view_fragment_text);
			viewHolder.text.setMaxLines(5);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Note note = getItem(position);
		if (note.getTitle().length() > 0) {
			viewHolder.title.setVisibility(View.VISIBLE);
			viewHolder.title.setText(note.getTitle());
		} else {
			viewHolder.title.setVisibility(View.GONE);
		}
		viewHolder.text.setText(note.getText());
		convertView.setBackgroundColor(note.getColor());
		return convertView;
	}

	@Override
	public long getItemId(int i) {
		return getItem(i).getId();
	}

	private static class ViewHolder {

		public TextView title;

		public TextView text;
	}
}
