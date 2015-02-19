package com.ytnuk.notes.note;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

//TODO: note attachments (photo/video/audio, phone, link, mail, location)
//TODO: attachments in GridView under text...
//TODO: pin/star note
//TODO: event in calendar / alarm reminder.. is it possible using sharing ?
//TODO: archive notes
//TODO: add tasks under note text
//TODO: need to inspire by design from Google Keep for note list
public class Note extends RealmObject implements Serializable {

	private String title;

	private String text;

	private int color;

	private Date created;

	private Date edited;

	public long getId() {
		return row.getIndex();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getEdited() {
		return edited;
	}

	public void setEdited(Date edited) {
		this.edited = edited;
	}
}
