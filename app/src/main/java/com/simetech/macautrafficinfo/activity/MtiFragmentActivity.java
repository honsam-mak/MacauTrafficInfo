package com.simetech.macautrafficinfo.activity;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.AboutFragment;

/**
 * Created by honsam on 8/25/14.
 */
public class MtiFragmentActivity extends FragmentActivity {

    private AdView adView;
    private static final String DEVICE_ID = "DEVICE ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadAdsView();
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

    /*
     * To load up the AdsView
     */
    private void loadAdsView() {

        adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(DEVICE_ID)
                .build();
        adView.loadAd(adRequest);
    }
}
