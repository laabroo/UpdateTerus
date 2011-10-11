package com.laabroo.android.updaterus;

//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.laabroo.android.updaterus.network.GetData;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private GetData getData;
	private ImageView imageView;
	private TextView cute;
	private TextView follow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Thread thread = null;
		Runnable runnable = new Update();
		thread = new Thread(runnable);
		thread.start();

	}

	private void showData() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					getImage();
				}

				catch (Exception e) {
					Log.i(TAG, e.getMessage());
				}
			}
		});

	}

	private void getImage() {
		try {

			if (getData == null) {
				getData = new GetData();
			}

			String data = getData.loadData();

			JSONObject jsonObject = (JSONObject) new JSONTokener(data)
					.nextValue();
			String user = jsonObject.getString("id");
			String name = jsonObject.getString("name");
			String cute = jsonObject.getString("cute");
			String follow = jsonObject.getString("follow");
			Log.i(TAG + " => Id user : ", user);
			String url = "http://updaterus.com/images/users/" + user + "/1.jpg";
			// WebView webView = (WebView) findViewById(R.id.webView1);
			// webView.getSettings().setJavaScriptEnabled(true);
			// webView.loadUrl(url);

			Drawable image = ImageOperations(this, url, "image.jpg");
			imageView = new ImageView(this);
			imageView = (ImageView) findViewById(R.id.image);
			imageView.setImageDrawable(image);

			TextView valueName = (TextView) findViewById(R.id.main_nama);
//			TextView valueFollow = (TextView) findViewById(R.id.main_folow);
//			TextView valueCute = (TextView) findViewById(R.id.main_cute);
			valueName.setText(name);
//			valueFollow.setText(follow);
//			valueCute.setText(cute);

			Log.i(TAG, data);
		}

		catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			goToAbout();
			break;
		case R.id.menu_details:
			goToDetails();
			break;

		}
		return true;
	}

	private void goToAbout() {
		Intent intent = new Intent(this, About.class);
		startActivity(intent);

	}

	private void goToDetails() {
		Intent intent = new Intent(this, Details.class);
		startActivity(intent);
	}

	private Drawable ImageOperations(Context ctx, String url,
			String saveFilename) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object fetch(String address) throws MalformedURLException,
			IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}

	class Update implements Runnable {

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					showData();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().isInterrupted();
				}

				catch (Exception e) {
					Log.i(TAG, e.getMessage());
				}

			}

		}
	}

}