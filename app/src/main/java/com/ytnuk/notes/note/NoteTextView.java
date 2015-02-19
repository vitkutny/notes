package com.ytnuk.notes.note;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ytnuk.notes.R;

public class NoteTextView extends TextView {

	private Rect rect = new Rect();

	private Paint paint = new Paint();

	public NoteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(getResources().getColor(R.color.gray));
		paint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < getLineCount(); i++) {
			int baseline = getLineBounds(i, rect);
			canvas.drawLine(rect.left, baseline + 1, rect.right, baseline + 1, paint);
		}
		super.onDraw(canvas);
	}
}
