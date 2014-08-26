package com.simetech.macautrafficinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.simetech.macautrafficinfo.fragment.ParkingInfoArrayListFragment;

public class ParkingInfoActivity extends MtiFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        // Create the list fragment and add it as our sole content.
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
            Fragment list = new ParkingInfoArrayListFragment();
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, list).commit();
        }
	}
}
