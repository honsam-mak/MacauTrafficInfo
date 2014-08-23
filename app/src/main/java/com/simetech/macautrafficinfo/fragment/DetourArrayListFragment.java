package com.simetech.macautrafficinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.simetech.macautrafficinfo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetourArrayListFragment extends ListFragment {
	
	private static final String URL = "http://www.dsat.gov.mo/tc/croad.aspx";
	
	private String[] idList;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
						
		String[] data = doFetch();
		
		if (data != null) {
			setListAdapter(new ArrayAdapter<String>(getActivity(), 
												R.layout.detourlist,
												android.R.id.text1,
												data));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
//        Log.i("DetourArrayListFragment", "Item clicked: " + id);
        
        DetourDetailFragment newFragment = DetourDetailFragment.newInstance(idList[position]);
        newFragment.show(getFragmentManager(), "dialog");
	}
	
	private String[] doFetch() {
		
		String[] val = null;
		
		//Extract image from URL
		Document doc = null;
		
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (doc != null) {		
			Elements cols = doc.select("td.MainContentText a[href]");
			val = new String[cols.size()];
			idList = new String[cols.size()];
			int i = 0;
			
			for(Element col: cols) {
				val[i] = col.text();
				idList[i++] =col.baseUri().substring(0, 26) + col.attr("href").toString();		
//				System.out.println(col.text());
			}
			
			
		}
		
		return val;
	}
}
