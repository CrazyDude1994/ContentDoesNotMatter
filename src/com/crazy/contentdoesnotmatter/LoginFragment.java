package com.crazy.contentdoesnotmatter;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.auth_dialog, container, false);
		WebView webView = (WebView)view.findViewById(R.id.webView);
		
		final class InstagramWebViewClient extends WebViewClient {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http://localhost/redirect#")) {
					
					return false;
				} else {
					return true;
				}
			}
		}
		
		webView.setWebViewClient(new InstagramWebViewClient());
		webView.loadUrl("https://instagram.com/oauth/authorize/?client_id=7984b1f66b0a4eb4b31cbb59039e4524&redirect_uri=http://localhost/redirect&response_type=token");
		return view;
	}

}
