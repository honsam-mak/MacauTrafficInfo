package com.simetech.macautrafficinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.simetech.macautrafficinfo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetourDetailFragment extends DialogFragment {
	
	public static DetourDetailFragment newInstance(String url) {
		DetourDetailFragment myDetailFragment = new DetourDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		myDetailFragment.setArguments(bundle);
		
		return myDetailFragment; 
	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		String url = getArguments().getString("url");
		System.out.println(url);
		
		View mainView = inflater.inflate(R.layout.webview_detour, container, false);
		
		WebView wv = (WebView) mainView.findViewById(R.id.wv_detour);
		
		if (wv != null) {
			wv.getSettings().setDefaultTextEncodingName("utf-8");
//			wv.getSettings().setDefaultFontSize(4);
//			wv.getSettings().setDefaultZoom(ZoomDensity.FAR);
		}
		
		Document doc = null;
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String summary = "";
		if (doc != null) {
			Element info = doc.select("td.MainContentText").first();
			
			if (info.children().size() >= 5) {
				Element content = info.child(4);
				
				summary = "<p>" + info.child(0).html() + "</p>"				//Title 
						+ "<p/>"
						+ "<table>" + info.child(2).html() + "</table>"		//Table
						+ "<p/>";
				
				Elements contents = content.getElementsByTag("textformat");
				for (Element c : contents) {
					if (c.children().size() > 0 && c.child(0).children().size() > 0)
						summary += "<p>" + c.child(0).child(0).html() + "</p>";
				}
								
//				System.out.println(summary);
			}
		}
				
		if (wv != null) {
			wv.loadDataWithBaseURL(null, summary, "text/html", "utf-8", null);
		}
		
		getDialog().setCanceledOnTouchOutside(true);
		
		return mainView;
	}	
}
