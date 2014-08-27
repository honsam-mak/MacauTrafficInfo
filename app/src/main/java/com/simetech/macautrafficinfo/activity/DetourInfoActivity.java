package com.simetech.macautrafficinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.DetourArrayListFragment;

public class DetourInfoActivity extends MtiFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detour);

        // Create the list fragment and add it as our sole content.
        if (getFragmentManager().findFragmentById(R.id.detourfragment) == null) {
        	Fragment listFragment = new DetourArrayListFragment();
        	getSupportFragmentManager().beginTransaction().replace(R.id.detourfragment, listFragment).commit();
        }
	}


}
