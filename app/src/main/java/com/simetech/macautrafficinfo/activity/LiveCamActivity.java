package com.simetech.macautrafficinfo.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.simetech.macautrafficinfo.R;
import com.simetech.macautrafficinfo.fragment.AboutFragment;
import com.simetech.macautrafficinfo.fragment.LiveCamWebFragment;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

import static com.simetech.macautrafficinfo.constant.MtiConstants.LOCATION_ID;

public class LiveCamActivity extends MtiFragmentActivity {

    private boolean scrolling = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

        initWheel(R.id.cam_station);
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

    /*
     * This listener is used for Android-wheel plugin which listens on scroll events
     */
    private final OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
            scrolling = true;
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            scrolling = false;
            updateWebImage(wheel.getCurrentItem());
        }
    };

   /*
    * This listener is used for Android-wheel plugin which listens on changed event
    */
    private final OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            Log.d("Camera", "onChanged, wheelScrolled = " + scrolling);
            if(!scrolling) {
                //TODO: update status
            }
        }
    };

    /*
     * To update the live camera image.
     */
    private void updateWebImage(int index) {

        Fragment fragment = new LiveCamWebFragment();
        Bundle args = new Bundle();
        args.putInt(LiveCamWebFragment.ARG_SECTION_NUMBER, LOCATION_ID[index]);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    /*
     * To initialize the wheel component.
     */
    private void initWheel(int id) {
        WheelView wheel = (WheelView) findViewById(id);
        wheel.setVisibleItems(5);
        wheel.setCurrentItem(0);
        wheel.setViewAdapter(new StationAdapter(this));
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);

        updateWebImage(0);
    }

    /*
     * To create a custom adapter for wheel.
     */
    private class StationAdapter extends AbstractWheelTextAdapter {

        private String stations[] = getResources().getStringArray(R.array.camera_arrays);

        protected StationAdapter(Context context) {
            super(context);

            setTextColor(0xFFFF8000);
            setTextSize(18);
        }

        @Override
        public int getItemsCount() {
            return stations.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return stations[index];
        }
    }
}
