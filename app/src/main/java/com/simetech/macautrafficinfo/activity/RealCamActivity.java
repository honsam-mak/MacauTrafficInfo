package com.simetech.macautrafficinfo.activity;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.AboutFragment;
import com.simetech.macautrafficinfo.fragment.RealCamWebFragment;

public class RealCamActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	private static final int locationId[] = { 68, //title_section1
		                                      67, //title_section2
		                                      66, //title_section3
		                                      65, //title_section4
		                                      62, //title_section5
		                                      61, //title_section6
		                                      60, //title_section7
		                                      59, //title_section8
		                                      58, //title_section9
		                                      49, //title_section10
		                                      50, //title_section11
		                                      64, //title_section12
		                                      63, //title_section13
		                                      51, //title_section14
		                                      52, //title_section15
										    };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						R.layout.dropdownlist,
						android.R.id.text1, getResources().getStringArray(R.array.camera_arrays)), this);
				
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
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
	
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		
		Fragment fragment = new RealCamWebFragment();
		Bundle args = new Bundle();
		args.putInt(RealCamWebFragment.ARG_SECTION_NUMBER, locationId[position]);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		
		return true;
	}

}
