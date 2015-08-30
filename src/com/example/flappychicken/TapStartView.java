package com.example.flappychicken;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class TapStartView extends View {

	public TapStartView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	Paint paint = new Paint();
	Resources res = this.getContext().getResources();

	Bitmap back = BitmapFactory.decodeResource(res, R.drawable.background2);
	int x = 0;
	int dx = 10;

	Bitmap chicken = BitmapFactory.decodeResource(res, R.drawable.flappy4tap);

	private int chickenW = chicken.getWidth() / 4;
	private int chickenH = chicken.getHeight();
	private int frame = 0;

	private int chickenX = 70;
	private int chickenY = 100;
	private int chickenDy = 3;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自動生成されたメソッド・スタブ
		Rect srcScroll = new Rect(0, 0, back.getWidth(), back.getHeight());
		Rect dstScroll = new Rect(0 + x, 0, getWidth() + x, getHeight());

		Rect dst2 = new Rect(0 + x + getWidth(), 0,
				x + getWidth() + getWidth(), getHeight());

		canvas.drawBitmap(back, srcScroll, dstScroll, paint);
		canvas.drawBitmap(back, srcScroll, dst2, paint);

		
		
		if(chickenY < 110){
			chickenY += chickenDy;
		}else if(chickenY > 90){
			chickenY -= chickenDy;
		}
		
		int srcX = frame * chickenW;
		Rect src = new Rect(srcX, 0, srcX + chickenW, chickenH);
		Rect dst = new Rect(chickenX, chickenY, chickenX + chickenW, chickenY
				+ chickenH);

		canvas.drawBitmap(chicken, src, dst, paint);

		frame = ++frame % 4;

		invalidate();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

