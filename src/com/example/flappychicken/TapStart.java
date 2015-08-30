package com.example.flappychicken;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class TapStart extends Activity {

	ImageView img;
	TextView highScore;
	
	SharedPreferences pref;
	String kekka;
	
	MainActivity mainAct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		TapStartView tsv = new TapStartView(this);
		setContentView(tsv);
		
		
		View view1 = getLayoutInflater().inflate(R.layout.tap_start, null);
		addContentView(view1, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
		highScore = (TextView)findViewById(R.id.highscore);
		pref = getSharedPreferences("main", MODE_PRIVATE);
		kekka = pref.getString("score", "0");
		highScore.setText("HIGH SCORE : " + kekka);
		
		img = (ImageView)findViewById(R.id.imageView1);
		img.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自動生成されたメソッド・スタブ
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					Intent i = new Intent(TapStart.this, MainActivity.class);
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					finish();
					break;
				}
				return false;
			}
		});
		
	}

}

