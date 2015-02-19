package com.ytnuk.notes.note.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ytnuk.notes.note.Note;

import io.realm.RealmResults;

public class PageAdapter extends FragmentStatePagerAdapter {

	private RealmResults<Note> notes;

	public PageAdapter(FragmentManager fragmentManager, RealmResults<Note> notes) {
		super(fragmentManager);
		this.notes = notes;
	}

	@Override
	public Fragment getItem(int position) {
		return ViewFragment.newInstance(notes.get(position).getId());
	}

	@Override
	public int getCount() {
		return notes.size();
	}
}
