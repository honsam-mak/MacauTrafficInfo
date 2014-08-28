package com.simetech.macautrafficinfo.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.simetech.macautrafficinfo.R;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ParkingInfoArrayListFragment extends ListFragment {
	
	private static final String URL = "http://m.dsat.gov.mo/carpark.aspx";

    ArrayList<String> places = new ArrayList<String>();
    ArrayList<String> tcs = new ArrayList<String>();
    ArrayList<String> carSpace = new ArrayList<String>();
    ArrayList<String> motorSpace = new ArrayList<String>();
    ArrayList<String>[] infos = new ArrayList[4];

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        doFetch();
                    
        setListAdapter(new CustomAdapter(getActivity(), R.layout.parkinglist, infos));    
                    
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Activity activity = getActivity();
				if (activity != null) {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							doFetch();
							notifyData();
						}
					});
				} else {
					cancel();
				}
			}
		}, 10000, 10000);
        
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }
    
    public void notifyData() {
        ((CustomAdapter) getListAdapter()).updateInfo(infos);
    }
    
    private void doFetch() {

		// Extract image from URL
		Document doc = null;
		
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
          		
		if (doc != null) {
			Elements cols = doc.select("div#carpark_data tr");
			places.clear();
			tcs.clear();
			carSpace.clear();
			motorSpace.clear();
			
			for(Element col: cols) {
				String items[] = col.text().split(" ");
				places.add(items[0]);
				tcs.add(items[1] + " " + items[2]);
				carSpace.add(items[3]);
				motorSpace.add(items[4]);
			}
		}	    	    		        
		
		infos[0] = places;
		infos[1] = tcs;
		infos[2] = carSpace;
		infos[3] = motorSpace;
		
    }

    private class CustomAdapter extends ArrayAdapter<ArrayList<String>> {

        private LayoutInflater mInflater;
        private ArrayList<String> mPlaces = new ArrayList<String>();
        private ArrayList<String> mTcs = new ArrayList<String>();
        private ArrayList<String> mCarSpace = new ArrayList<String>();
        private ArrayList<String> mMotorSpace = new ArrayList<String>();

        class ViewHolder {
            TextView tvPlace;
            TextView tvTc;
            TextView tvCarSpace;
            TextView tvMotorSpace;
        }

        public CustomAdapter(Context context, int resId, ArrayList<String>[] values) {
            super(context, resId, values);
            mInflater = LayoutInflater.from(context);
            mPlaces = values[0];
            mTcs = values[1];
            mCarSpace = values[2];
            mMotorSpace = values[3];
        }
        
        public void updateInfo(ArrayList<String>[] values) {
            mPlaces = values[0];
            mTcs = values[1];
            mCarSpace = values[2];
            mMotorSpace = values[3];

            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.parkinglist_icon_text, null);

                holder = new ViewHolder();
                holder.tvPlace = (TextView) convertView.findViewById(R.id.place);
                holder.tvTc = (TextView) convertView.findViewById(R.id.current_tc);
                holder.tvCarSpace = (TextView) convertView.findViewById(R.id.car);
                holder.tvMotorSpace = (TextView) convertView.findViewById(R.id.motor);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvPlace.setText(mPlaces.get(position));
            holder.tvTc.setText(mTcs.get(position));
            holder.tvCarSpace.setText(mCarSpace.get(position));
            holder.tvMotorSpace.setText(mMotorSpace.get(position));

            setTextViewColor(holder.tvCarSpace, mCarSpace.get(position));
            setTextViewColor(holder.tvMotorSpace, mMotorSpace.get(position));

            return convertView;
        }

        @Override
        public int getCount() {
            return mPlaces.size();
        }

        private void setTextViewColor(TextView tv, String value) {

            if(!StringUtil.isNumeric(value)) {
                tv.setBackgroundColor(Color.DKGRAY);
                return;
            }
            // red
            if(Integer.parseInt(value) <= 3){
                tv.setBackgroundColor(Color.RED);
                return;
            }
            //yellow
            if(Integer.parseInt(value) < 10){
                tv.setBackgroundColor(Color.rgb(0xc2, 0xc2, 0x66));
                return;
            }
            //green
            tv.setBackgroundColor(Color.rgb(0x22, 0x99, 0x22));
        }
    }
}
