package com.example.flappychicken;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {
	Paint paint = new Paint();
	Resources res = this.getContext().getResources();

	Bitmap backGround = BitmapFactory
			.decodeResource(res, R.drawable.background);
	Bitmap chicken = BitmapFactory.decodeResource(res, R.drawable.flappy4);
	Bitmap chickenFall = BitmapFactory.decodeResource(res,
			R.drawable.flappy_fall);
	Bitmap chickenGround = BitmapFactory.decodeResource(res,
			R.drawable.flappy_ground);
	Bitmap coin = BitmapFactory.decodeResource(res, R.drawable.coin);
	Bitmap coin2 = BitmapFactory.decodeResource(res, R.drawable.coin);
	Bitmap enemy = BitmapFactory.decodeResource(res, R.drawable.enemy);
	Bitmap enemy2 = BitmapFactory.decodeResource(res, R.drawable.enemy);
	Bitmap backS = BitmapFactory.decodeResource(res, R.drawable.background2);
	Bitmap scoreNum = BitmapFactory.decodeResource(res, R.drawable.score_num);

	private SoundPool sPool;
	private int sJump, sCoin, sEnemy, sFoot, sFall;

	// back scroll
	int backX = 0;
	int backDx = 10;
	int backY = 90;

	// chicken
	private int chickenW = chicken.getWidth() / 4;
	private int chickenH = chicken.getHeight();
	private int frame = 0;

	private int chickenX = 70;
	private int chickenY = 0;
	private int chickenDy = 0;
	private int chickenF = 1;
	private int touchY = 0;

	// coin & enemy
	private int coinX = 200;
	private int coinX2 = 300;
	private int coinY = 300;
	private int coinY2 = 100;
	private int coinDx = -10;
	private int enemyX = 50;
	private int enemyY = 200;
	private int enemyX2 = 100;
	private int enemyY2 = 500;
	private int enemyDx = -5;
	private int coinW = coin.getWidth() / 4;
	private int coinH = coin.getHeight();

	// score num
	private int scoreW = scoreNum.getWidth() / 30;
	private int scoreH = scoreNum.getHeight();
	private int scoreFrame = 0;

	public MainView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
		sPool = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
		sJump = sPool.load(context, R.raw.jump, 1);
		sCoin = sPool.load(context, R.raw.coin, 1);
		sEnemy = sPool.load(context, R.raw.enemy, 1);
		sFoot = sPool.load(context, R.raw.foot, 1);
		sFall = sPool.load(context, R.raw.fall, 1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// back
		Rect srcBackground = new Rect(0, 0, backGround.getWidth(),
				backGround.getHeight());
		Rect dstBackground = new Rect(0, 0, getWidth(), getHeight());

		canvas.drawBitmap(backGround, srcBackground, dstBackground, paint);

		// back scroll
		Rect srcBack = new Rect(0, 0, backS.getWidth(), backS.getHeight());
		Rect dstBack = new Rect(0 + backX, 0, getWidth() + backX, getHeight());

		Rect dstBack2 = new Rect(0 + backX + getWidth(), 0, backX + getWidth()
				+ getWidth(), getHeight());

		canvas.drawBitmap(backS, srcBack, dstBack, paint);
		canvas.drawBitmap(backS, srcBack, dstBack2, paint);

		if (backX > getWidth() * -1) {
			backX -= backDx;
		} else {
			backX = 0;
		}

		// chicken
		int srcX = frame * chickenW;
		Rect src = new Rect(srcX, 0, srcX + chickenW, chickenH);
		Rect dst = new Rect(chickenX, chickenY, chickenX + chickenW, chickenY
				+ chickenH);

		switch (chickenF) {
		case 0:
			canvas.drawBitmap(chickenGround, src, dst, paint);
			sPool.play(sFoot, 1.0f, 1.0f, 0, 0, 1.0f);
			break;
		case 1:
			canvas.drawBitmap(chicken, src, dst, paint);
			break;
		case 2:
			canvas.drawBitmap(chickenFall, src, dst, paint);
			sPool.play(sFall, 1.0f, 1.0f, 0, 0, 1.0f);
			break;
		}


		if (touchY < chickenY) {
			chickenF = 2;
		}

		frame = ++frame % 4;

		chickenY += chickenDy;
		chickenDy += 6;
		if (chickenY > getHeight() - chickenH - backY) {
			chickenY = getHeight() - chickenH - backY;
			chickenF = 0;
		}
		if (chickenY < 0) {
			chickenY = 0;

		}

		// score

		int srcScore = scoreFrame * scoreW;
		Rect srcS = new Rect(srcScore, 0, srcScore + scoreW, scoreH);
		Rect dstS = new Rect(getWidth() / 2 - scoreW / 2, 30, getWidth() / 2
				- scoreW / 2 + scoreW, 30 + scoreH);

		canvas.drawBitmap(scoreNum, srcS, dstS, paint);

		// coin & enemy
		int srcC = frame * coinW;

		if (coinX < 0 - coinW) {
			coinX = getWidth();
			Random rnd = new Random();
			coinY = getHeight() - backY - coinH
					- rnd.nextInt(getHeight() - backY - coinH);
		}
		if (coinX2 < 0 - coinW) {
			coinX2 = getWidth();
			Random rnd = new Random();
			coinY2 = getHeight() - backY - coinH
					- rnd.nextInt(getHeight() - backY - coinH);
		}
		if (enemyX < 0 - coinW) {
			enemyX = getWidth();
			Random rnd = new Random();
			enemyY = getHeight() - backY - coinH
					- rnd.nextInt(getHeight() - backY - coinH);
		}

		if (enemyX2 < 0 - coinW) {
			enemyX2 = getWidth();
			Random rnd = new Random();
			enemyY2 = getHeight() - backY - coinH
					- rnd.nextInt(getHeight() - backY - coinH);
		}

		Rect srcCoin = new Rect(srcC, 0, srcC + coinW, coinH);
		Rect coinDst = new Rect(coinX, coinY, coinX + coinW, coinY + coinH);
		Rect coinDst2 = new Rect(coinX2, coinY2, coinX2 + coinW, coinY2 + coinH);
		Rect dstEnemy = new Rect(enemyX, enemyY, enemyX + coinW, enemyY + coinH);
		Rect dstEnemy2 = new Rect(enemyX2, enemyY2, enemyX2 + coinW, enemyY2
				+ coinH);

		canvas.drawBitmap(coin, srcCoin, coinDst, paint);
		canvas.drawBitmap(coin2, srcCoin, coinDst2, paint);
		canvas.drawBitmap(enemy, srcCoin, dstEnemy, paint);
		canvas.drawBitmap(enemy2, srcCoin, dstEnemy2, paint);

		coinX += coinDx;
		coinX2 += coinDx;
		enemyX += enemyDx;
		enemyX2 += enemyDx;

		// check for collision
		checkForCollision();

		invalidate();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private void checkForCollision() {
		// TODO 自動生成されたメソッド・スタブ
		if (chickenY + chickenH > coinY && coinY + coinH > chickenY) {
			if (chickenX + chickenW > coinX && coinX + coinW > chickenX) {
				sPool.play(sCoin, 1.0f, 1.0f, 0, 0, 1.0f);
				coinX = getWidth();
				Random rnd = new Random();
				coinY = getHeight() - backY - coinH
						- rnd.nextInt(getHeight() - backY - coinH);
				scoreFrame++;
			}
		}
		if (chickenY + chickenH > coinY2 && coinY2 + coinH > chickenY) {
			if (chickenX + chickenW > coinX2 && coinX2 + coinW > chickenX) {
				sPool.play(sCoin, 1.0f, 1.0f, 0, 0, 1.0f);
				coinX2 = getWidth();
				Random rnd = new Random();
				coinY2 = getHeight() - backY - coinH
						- rnd.nextInt(getHeight() - backY - coinH);
				scoreFrame++;
			}
		}

		if (chickenX + chickenW > enemyX && enemyX + coinW > chickenX) {
			if (chickenY + chickenH > enemyY && enemyY + coinH > chickenY) {
				sPool.play(sEnemy, 1.0f, 1.0f, 0, 0, 1.0f);
				enemyX = getWidth();
				Random rnd = new Random();
				enemyY = getHeight() - backY - coinH
						- rnd.nextInt(getHeight() - backY - coinH);
				if (chickenY < getHeight() - backY - chickenH - 100) {
					chickenY += 100;
				} else {
					chickenY = getHeight() - backY - chickenH;
				}
				if (scoreFrame == 0) {
					scoreFrame = 0;
				} else {
					scoreFrame--;
				}
			}
		}
		if (chickenX + chickenW > enemyX2 && enemyX2 + coinW > chickenX) {
			if (chickenY + chickenH > enemyY2 && enemyY2 + coinH > chickenY) {
				sPool.play(sEnemy, 1.0f, 1.0f, 0, 0, 1.0f);
				enemyX2 = getWidth();
				Random rnd = new Random();
				enemyY2 = getHeight() - backY - coinH
						- rnd.nextInt(getHeight() - backY - coinH);
				if (chickenY < getHeight() - backY - chickenH - 100) {
					chickenY += 100;
				} else {
					chickenY = getHeight() - backY - chickenH;
				}
				if (scoreFrame == 0) {
					scoreFrame = 0;
				} else {
					scoreFrame--;
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			chickenDy = -15;
			chickenF = 1;
			touchY = chickenY;
			sPool.play(sJump, 1.0f, 1.0f, 0, 0, 1.0f);
			break;
		}
		return true;
	}

	@Override
	protected void onDetachedFromWindow() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDetachedFromWindow();
		sPool.release();
	}

	public int getScoreFrame() {
		return scoreFrame;
	}

	public void setScoreFrame(int scoreFrame) {
		this.scoreFrame = scoreFrame;
	}

}

