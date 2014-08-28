package com.simetech.macautrafficinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.simetech.macautrafficinfo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetourArrayListFragment extends ListFragment {
	
	private static final String URL = "http://www.dsat.gov.mo/tc/croad.aspx";
	
	private String[] idList;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
						
        List<Map<String, String>> data = doFetchWebData();
		
		if (!data.isEmpty() && getListAdapter() == null) {
            SimpleAdapter adapter = new SimpleAdapter(
                                            getActivity(),
                                            data,
                                            R.layout.detourlist,
                                            new String[] {"period", "content"},
                                            new int[] {android.R.id.text1, android.R.id.text2}
                                        );
            setListAdapter(adapter);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

        DetourDetailFragment newFragment = DetourDetailFragment.newInstance(idList[position]);
        newFragment.show(getFragmentManager(), "dialog");
	}
	
	private List<Map<String, String>> doFetchWebData() {
		
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		//Extract image from URL
		Document doc = null;
		
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (doc != null) {		
			Elements cols = doc.select("td.MainContentText a[href]");
			idList = new String[cols.size()];
			int i = 0;

			for(Element col: cols) {
                String[] content = col.text().split(",", 2);

                Map map = new HashMap();
                map.put("period", content[0]);
                map.put("content", content[1]);
                list.add(map);
				idList[i++] =col.baseUri().substring(0, 26) + col.attr("href").toString();
			}
		}

        return list;
	}
}
