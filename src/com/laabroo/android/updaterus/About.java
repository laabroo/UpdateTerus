package com.laabroo.android.updaterus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class About extends Activity {
	private String url = "http://www.updaterus.com/about.html";
	private ProgressDialog dialog;
	private static final String TAG = "About";

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.about);
		loadData();
	}

	private void loadData() {
		final Activity activity = this;
		try {
			dialog = new ProgressDialog(this);
			dialog.setMessage("Please wait..");
			dialog.show();
			WebView webView = (WebView) findViewById(R.id.webView);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					activity.setProgress(progress * 1000);
					if (progress == 100 && dialog.isShowing())
						dialog.dismiss();
				}
			});
			webView.setWebViewClient(new WebViewClient() {
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					Toast.makeText(activity, "Upss..! " + description,
							Toast.LENGTH_SHORT).show();
				}
			});
			webView.loadUrl(url);
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	}
}
