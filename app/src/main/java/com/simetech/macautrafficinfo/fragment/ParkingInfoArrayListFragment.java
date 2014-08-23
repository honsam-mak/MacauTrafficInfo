package com.simetech.macautrafficinfo.fragment;

import android.app.Activity;
import android.content.Context;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ParkingInfoArrayListFragment extends ListFragment {
	
	private static final String URL = "http://m.dsat.gov.mo/carpark.aspx";
	
   	
	private static class customAdapter extends ArrayAdapter<ArrayList<String>> {

		private LayoutInflater mInflater;
		private ArrayList<String> mPlaces = new ArrayList<String>();
		private ArrayList<String> mTcs = new ArrayList<String>();
		private ArrayList<String> mCarspace = new ArrayList<String>();
		private ArrayList<String> mMotorspace = new ArrayList<String>();
        
		public customAdapter(Context context, int resId, ArrayList<String>[] values) {
			super(context, resId, values);
			mInflater = LayoutInflater.from(context);
			mPlaces = values[0];
			mTcs = values[1];
			mCarspace = values[2];
			mMotorspace = values[3];
   		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.parkinglist_icon_text, null);
				
				holder = new ViewHolder();
				holder.place = (TextView) convertView.findViewById(R.id.place);
				holder.tc = (TextView) convertView.findViewById(R.id.current_tc);
				holder.space_cars = (TextView) convertView.findViewById(R.id.car);
				holder.space_motors = (TextView) convertView.findViewById(R.id.motor);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
    		holder.place.setText((String)mPlaces.get(position));
			holder.tc.setText((String)mTcs.get(position));
			holder.space_cars.setText((String)mCarspace.get(position));
			holder.space_motors.setText((String)mMotorspace.get(position));

			return convertView;
		}
		
		@Override
		public int getCount() {
			return mPlaces.size();
		}
		
		static class ViewHolder {
			TextView place;
			TextView tc;
			TextView space_cars;
			TextView space_motors;
		}
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        doFetch();
                    
        setListAdapter(new customAdapter(getActivity(), R.layout.parkinglist, infos));    
                    
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
//        Log.i("FragmentList", "Item clicked: " + id);
    }
    
    public void notifyData() {
    	setListAdapter(new customAdapter(getActivity(), R.layout.parkinglist, infos));    
    }
    
    private void doFetch() {
//    	System.out.println("doFetch...");
    	
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
			carspace.clear();
			motorspace.clear();
			
			for(Element col: cols) {
				String items[] = col.text().split(" ");
				places.add(items[0]);
				tcs.add(items[1] + " " + items[2]);
				carspace.add(items[3]);
				motorspace.add(items[4]);
//				for(int i=0; i < items.length; i++)
//					System.out.println(items[i]);
			}
		}	    	    		        
		
		infos[0] = places;
		infos[1] = tcs;
		infos[2] = carspace;
		infos[3] = motorspace;
		
    }
    

	ArrayList<String> places = new ArrayList<String>();
	ArrayList<String> tcs = new ArrayList<String>();
	ArrayList<String> carspace = new ArrayList<String>();
	ArrayList<String> motorspace = new ArrayList<String>();
	static ArrayList<String>[] infos = new ArrayList[4];
}
