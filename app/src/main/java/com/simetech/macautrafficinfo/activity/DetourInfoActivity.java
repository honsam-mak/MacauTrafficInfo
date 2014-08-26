package com.simetech.macautrafficinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.simetech.macautrafficinfo.fragment.DetourArrayListFragment;

public class DetourInfoActivity extends MtiFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // Create the list fragment and add it as our sole content.
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
//        	DetourArrayListFragment list = new DetourArrayListFragment();
//            getFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
        	Fragment list = new DetourArrayListFragment();
        	getSupportFragmentManager().beginTransaction().replace(android.R.id.content, list).commit();
        }
	}


}
