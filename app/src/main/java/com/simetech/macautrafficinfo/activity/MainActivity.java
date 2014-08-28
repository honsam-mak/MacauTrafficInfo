package com.simetech.macautrafficinfo.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.controller.NetworkController;
import com.simetech.macautrafficinfo.fragment.AboutFragment;


public class MainActivity extends Activity {

	private boolean isNetworkConnected = false;

    private NetworkController nc = new NetworkController();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        isNetworkConnected = nc.isNetworkOn((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

		TextView rtCamera = (TextView) this.findViewById(R.id.button1);

		rtCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isNetworkConnected) {
					startActivity(new Intent(MainActivity.this, LiveCamActivity.class));

				} else
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_no_network), Toast.LENGTH_LONG).show();
			}

		});

		TextView rtParking = (TextView) this.findViewById(R.id.button2);

		rtParking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (isNetworkConnected) {
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
}
