package com.ytnuk.notes.note.form;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ColorAdapter extends BaseAdapter {

	private Context context;

	private Integer[] colors;

	public ColorAdapter(Context context, Integer[] colors) {
		this.context = context;
		this.colors = colors;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView view;
		if (convertView == null) {
			view = new ImageView(context);
			view.setImageResource(android.R.drawable.ic_menu_gallery);
		} else {
			view = (ImageView) convertView;
		}
		view.setBackgroundColor(getItem(position));
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return super.getDropDownView(position, convertView, parent);
	}

	@Override
	public int getCount() {
		return colors.length;
	}

	@Override
	public Integer getItem(int position) {
		return colors[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
