package com.crazy.contentdoesnotmatter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginFragment extends Fragment {
	
	private static final String CLIENT_ID = "7984b1f66b0a4eb4b31cbb59039e4524";
	private static final String REDIRECT_URL = "http://localhost/redirect";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_auth, container, false);
		WebView webView = (WebView)view.findViewById(R.id.webView);
		
		final class InstagramWebViewClient extends WebViewClient {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith(REDIRECT_URL)) {
					String accessToken = url.split(REDIRECT_URL + "#access_token=")[1]; // Get the access token
					FragmentManager fm = getActivity().getFragmentManager();
		            fm.popBackStack ("main", FragmentManager.POP_BACK_STACK_INCLUSIVE);
		            SharedPreferences preference = getActivity().getSharedPreferences(getString(R.string.preferense_file_name), Context.MODE_PRIVATE);
		            SharedPreferences.Editor editor = preference.edit();
		            editor.putString("accessToken", accessToken);
		            editor.commit();
					return true;
				} else {
					return false;
				}
			}
		}
		
		webView.setWebViewClient(new InstagramWebViewClient());
		webView.loadUrl("https://instagram.com/oauth/authorize/?client_id=" + CLIENT_ID
				+ "&redirect_uri=" + REDIRECT_URL + "&response_type=token");
		return view;
	}

}
