package com.laabroo.android.updaterus;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.laabroo.android.updaterus.network.DownloadImage;
import com.laabroo.android.updaterus.network.GetData;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private GetData getData;
	private ImageView imageView;
	private DownloadImage downloadImage;
	private Bitmap bitmap;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Thread thread = null;
		Runnable runnable = new Update();
		thread = new Thread(runnable);
		thread.start();
		Log.i(TAG, "Start thread.");

	}

	private void showData() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					getImage();
				} catch (Exception e) {
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
			if (downloadImage == null) {
				downloadImage = new DownloadImage();
			}

			String data = getData.loadData();
			JSONObject jsonObject = (JSONObject) new JSONTokener(data)
					.nextValue();
			String user = jsonObject.getString("id");
			Log.i("User : ", user);
			String name = jsonObject.getString("firstname");
			String last = jsonObject.getString("lastname");
			Log.i("Name : ", name + " " + last);
			String cute = jsonObject.getString("cute");
			Log.i("Cute : ", cute);
			String follow = jsonObject.getString("follow");
			Log.i("Follow : ", follow);

			textView = (TextView) findViewById(R.id.name_);
			textView.setText(name + " " + last);

			String url = "http://updaterus.com/images/users/" + user + "/1.jpg";

			bitmap = downloadImage.downloadImage(url);

			imageView = (ImageView) findViewById(R.id.image);
			imageView.setImageBitmap(bitmap);

			Log.i(TAG + " => Data user : ", user + " " + name + " " + cute
					+ " " + follow);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.menu_about:
		// goToAbout();
		// break;
		case R.id.menu_details:
			goToDetails();
			break;

		}
		return true;
	}

	// private void goToAbout() {
	// Intent intent = new Intent(this, About.class);
	// startActivity(intent);
	//
	// }

	private void goToDetails() {
		Intent intent = new Intent(this, Details.class);
		startActivity(intent);
	}

	class Update implements Runnable {

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					showData();
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					Thread.currentThread().isInterrupted();
				}

				catch (Exception e) {
					Log.i(TAG, e.getMessage());
					e.printStackTrace();
				}

			}

		}
	}

}