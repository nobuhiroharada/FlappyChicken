package com.example.flappychicken;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class StartView extends View {

	public StartView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	Paint paint = new Paint();
	Resources res = this.getContext().getResources();
	Bitmap test = BitmapFactory.decodeResource(res, R.drawable.background2);
	int x = 0;
	int dx = 10;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自動生成されたメソッド・スタブ

		Rect src = new Rect(0, 0, test.getWidth(), test.getHeight());
		Rect dst = new Rect(0 + x, 0, getWidth() + x, getHeight());
		
		Rect dst2 = new Rect(0 + x + getWidth(), 0, x + getWidth() + getWidth(), getHeight());
		
		canvas.drawBitmap(test, src, dst, paint);
		canvas.drawBitmap(test, src, dst2, paint);
		
		if (x > getWidth() * -1) {
			x -= dx;
		} else {
			x = 0;
		}

		invalidate();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

