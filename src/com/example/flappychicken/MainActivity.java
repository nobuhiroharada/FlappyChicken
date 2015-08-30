package com.example.flappychicken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MyCountDownTimer cdt;
	private TextView time;
	private TextView score;
	
	private MediaPlayer mediaPlayer = null;
	private SoundPool sPool;
	private int sCountDown;
	private int scoreV;
	private AlertDialog.Builder ad;
	MainView mainView;
	
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		mainView = new MainView(this);
		setContentView(mainView);

		View view1 = getLayoutInflater().inflate(R.layout.activity_main, null);
		addContentView(view1, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
		final ImageButton back = (ImageButton)findViewById(R.id.imageBack);
		back.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					back.setImageResource(R.drawable.back2);
					break;
				case MotionEvent.ACTION_UP:
					back.setImageResource(R.drawable.back);
					Intent intent = new Intent(MainActivity.this, TapStart.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					finish();
					break;
				}
				return false;
			}
		});
//		
//		mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
//		try {
//			mediaPlayer.prepare();
//		} catch (Exception e) {
//		}
//		mediaPlayer.start();

		time = (TextView) findViewById(R.id.timeTv);
		cdt = new MyCountDownTimer(30 * 1000, 1000);
		cdt.start();
				
		ad = new AlertDialog.Builder(this);
		
		sPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		sCountDown = sPool.load(this, R.raw.countdown, 1);
	}

	public class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			
			pref = getSharedPreferences("main", MODE_PRIVATE);
			Editor edit = pref.edit();
			int highScore = mainView.getScoreFrame();
			
			String scoreOld = pref.getString("score", "0");
			String scoreNew = String.valueOf(highScore);
			
			int oldNum = Integer.parseInt(scoreOld);
			int newNum = Integer.parseInt(scoreNew);
			
			if(oldNum < newNum){
				edit.putString("score", scoreNew);
			}else{
				edit.putString("score", scoreOld);
			}
			
			edit.commit();
			
			String kekka = pref.getString("score", "");

			time.setText("0");
			ad.setTitle("TIME OVER");
			
			ad.setMessage("SCORE: " + mainView.getScoreFrame() + "\n" + "HIGH SCORE: " + kekka);
			ad.setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO 自動生成されたメソッド・スタブ
					Intent intent = new Intent(MainActivity.this, Start.class);
					startActivity(intent);
					if (mediaPlayer != null) {
						mediaPlayer.reset();
						mediaPlayer.release();
						mediaPlayer = null;
					}
					if(sPool != null){
						sPool.release();
					}
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					finish();
				}
			});
			ad.show();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO 自動生成されたメソッド・スタブ
			time.setText("" + millisUntilFinished / 1000);
			long time2 = millisUntilFinished / 1000; 
			
			if(time2 < 6){
				sPool.play(sCountDown, 1.0f, 1.0f, 0, 0, 1.0f);
			}
		}
	}
	
	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		if(sPool != null){
			sPool.release();
		}
	}
	
}
