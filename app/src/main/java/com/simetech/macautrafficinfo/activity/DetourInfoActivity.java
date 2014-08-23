package com.simetech.macautrafficinfo.activity;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.AboutFragment;
import com.simetech.macautrafficinfo.fragment.DetourArrayListFragment;

public class DetourInfoActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        // Create the list fragment and add it as our sole content.
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
//        	DetourArrayListFragment list = new DetourArrayListFragment();
//            getFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
        	Fragment list = new DetourArrayListFragment();
        	getSupportFragmentManager().beginTransaction().replace(android.R.id.content, list).commit();
        }
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
		case android.R.id.home:
			onBackPressed();
			
			return true;
		case R.id.action_about:
			DialogFragment newFragment = AboutFragment.newInstance();
			newFragment.show(getFragmentManager(), "dialog");
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
