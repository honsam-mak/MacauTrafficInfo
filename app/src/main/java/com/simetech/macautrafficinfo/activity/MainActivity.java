package com.simetech.macautrafficinfo.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.AboutFragment;

public class MainActivity extends Activity {

	private boolean isNetworkConnected = false;
	
	private Interpolator accelerator = new AccelerateInterpolator();
	private Interpolator decelerator = new DecelerateInterpolator();
	private LinearLayout maincontainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		maincontainer = (LinearLayout) findViewById(R.id.maincontainer);
		
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        initNetwork();
        
		TextView rtCamera = (TextView) this.findViewById(R.id.button1);
		
		rtCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (isNetworkConnected) {
					flipit();
					startActivity(new Intent(MainActivity.this, RealCamActivity.class));

				} else
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_no_network), Toast.LENGTH_LONG).show();
			}
			
		});
		
		TextView rtParking = (TextView) this.findViewById(R.id.button2);
		
		rtParking.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (isNetworkConnected) {
					flipit();
					startActivity(new Intent(MainActivity.this, ParkingInfoActivity.class));
				}else
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_no_network), Toast.LENGTH_LONG).show();
			}
		});
		
		
		TextView rtDetour = (TextView) this.findViewById(R.id.button3);
		
		rtDetour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (isNetworkConnected) {
					flipit();
					startActivity(new Intent(MainActivity.this, DetourInfoActivity.class));
				}else
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_no_network), Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			DialogFragment newFragment = AboutFragment.newInstance();
			newFragment.show(getFragmentManager(), "dialog");
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initNetwork() {
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnected())
			isNetworkConnected = true;
	}
		
	private void flipit() {
		
		ObjectAnimator visToInvis = ObjectAnimator.ofFloat(maincontainer, "rotationY", 0f, -90f);
		visToInvis.setDuration(1000);
		visToInvis.setInterpolator(accelerator);

		final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(maincontainer, "rotationY", 90f, 0f);
		invisToVis.setDuration(1000);
		invisToVis.setInterpolator(decelerator);
		
		visToInvis.addListener(new AnimatorListener() {

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				invisToVis.start();
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		visToInvis.start();
	}
}
