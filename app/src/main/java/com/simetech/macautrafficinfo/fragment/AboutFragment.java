package com.simetech.macautrafficinfo.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simetech.macautrafficinfo.R;

import java.io.IOException;
import java.io.InputStream;

public class AboutFragment extends DialogFragment {

	public static AboutFragment newInstance() { return new AboutFragment(); }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View v = inflater.inflate(R.layout.about_dialog, container, false);
        
		try {
			String fn = getResources().getString(R.string.about_dialog_asset);
						
			InputStream is = getActivity().getAssets().open(fn);
			
			int size = is.available();
			
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			
			String text = new String(buffer);
			
			TextView tv = (TextView) v.findViewById(R.id.about_text);
			tv.setText(text);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        
        return v;
    }
}
