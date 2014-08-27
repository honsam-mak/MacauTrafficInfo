package com.simetech.macautrafficinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.ParkingInfoArrayListFragment;

public class ParkingInfoActivity extends MtiFragmentActivity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
		
        // Create the list fragment and add it as our sole content.
        if (getFragmentManager().findFragmentById(R.id.parkingfragment) == null) {
            Fragment listFragment = new ParkingInfoArrayListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.parkingfragment, listFragment).commit();
        }
	}
}
