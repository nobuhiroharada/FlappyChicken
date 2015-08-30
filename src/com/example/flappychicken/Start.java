package com.example.flappychicken;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Start extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		StartView scroll = new StartView(this);
		setContentView(scroll);

		View view1 = getLayoutInflater().inflate(R.layout.start, null);
		addContentView(view1, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		final ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					play.setImageResource(R.drawable.play2);
					break;
				case MotionEvent.ACTION_UP:
					play.setImageResource(R.drawable.play);
					Intent intent = new Intent(Start.this, TapStart.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					finish();
					break;
				}
				return false;
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		ImageView animTop = (ImageView) findViewById(R.id.topChicken);
		Animation Anim0 = AnimationUtils.loadAnimation(this, R.anim.anim_top);
		animTop.startAnimation(Anim0);
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();

	}
}

