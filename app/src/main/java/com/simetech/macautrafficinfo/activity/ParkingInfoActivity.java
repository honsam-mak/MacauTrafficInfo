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
	}
}
