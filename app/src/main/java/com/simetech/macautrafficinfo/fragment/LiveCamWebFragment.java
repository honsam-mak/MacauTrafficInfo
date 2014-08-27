package com.simetech.macautrafficinfo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.simetech.macautrafficinfo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LiveCamWebFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private static final String URL = "http://www.dsat.gov.mo/realtime_core.aspx?lang=tc&cam_id=";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.webview_livecam, container, false);
		
		final ProgressBar progressHorizontal = (ProgressBar) mainView.findViewById(R.id.cam_progress);
		
		// Extract image from URL
		Document doc = null;
		String imgURL = null;
		String url = URL +  Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER));
//		System.out.println(url);
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (doc != null) {
			Elements imgs = doc.select("img[src$=.jpg]");
			for(Element img: imgs) {
				imgURL = img.attr("src");
//				System.out.println(imgURL);
			}
			
			final WebView wv = (WebView) mainView.findViewById(R.id.wv);
			
			if(wv != null) {
//				wv.setInitialScale(100);
//				wv.getSettings().setBuiltInZoomControls(true);
//				wv.getSettings().setUseWideViewPort(true);
//				wv.getSettings().setDefaultZoom(ZoomDensity.FAR);
						
				// Disable copy and paste
				wv.setOnLongClickListener(new View.OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						return true;
					}
				});
				
				String image = "<center><img src='" + imgURL + "'></center>";
				
				wv.loadData(image, "text/html", "utf-8");
			}
			
			new Timer().schedule(new TimerTask(){
				int counter = 0;
				@Override
				public void run() {			
					final Activity activity = getActivity();
					if (activity != null) {
						
						counter ++;
						activity.runOnUiThread(new Runnable() {
							public void run() {
								if (counter %5 == 0) {
									wv.reload();
									counter = 0;
									progressHorizontal.setProgress(100);
								} else {
									progressHorizontal.incrementProgressBy(-25);
								}
							}
						});

					} else
						cancel();
				}
			}, 0, 1000);
		}
		return mainView;
	}
}
