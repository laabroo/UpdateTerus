package com.laabroo.android.updaterus;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.laabroo.android.updaterus.network.GetData;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Details extends Activity {
	private GetData getData;
	private static final String TAG = "Details";
	private String first_name;
	private String last_name;
	private String facebook;
	private String twitter;
	private String location;
	private String occupation;
	private String interest;
	private String birthday;
	private String cute;
	private String follow;

	private TextView valueName;
	private TextView valueFB;
	private TextView valueTwitter;
	private TextView valueLocation;
	private TextView valueOccupation;
	private TextView valueInterest;
	private TextView valueBirthDay;
	private TextView valueCute;
	private TextView valueFollow;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.details);

		valueName = (TextView) findViewById(R.id.labelName);
		valueFB = (TextView) findViewById(R.id.label_fb);
		valueTwitter = (TextView) findViewById(R.id.label_twitter);
		valueInterest = (TextView) findViewById(R.id.label_Interest);
		valueOccupation = (TextView) findViewById(R.id.label_Occupation);
		valueLocation = (TextView) findViewById(R.id.label_location);
		valueBirthDay = (TextView) findViewById(R.id.label_brithday);
		valueFollow = (TextView) findViewById(R.id.label_Follow);
		valueCute = (TextView) findViewById(R.id.label_Cute);
		setData();

	}

	private void setData() {

		try {
			if (getData == null) {
				getData = new GetData();
			}

			String data = getData.loadData();
			Log.i(TAG, data);

			JSONObject jsonObject = (JSONObject) new JSONTokener(data)
					.nextValue();
			Log.i(TAG + "json-obj : ", jsonObject.toString());
			first_name = jsonObject.getString("firstname");
			last_name = jsonObject.getString("lastname");
			facebook = jsonObject.getString("facebook");
			twitter = jsonObject.getString("twitter");
			location = jsonObject.getString("location");
			birthday = jsonObject.getString("birthday");
			interest = jsonObject.getString("hobby");
			cute = jsonObject.getString("cute");
			occupation = jsonObject.getString("occupation");
			follow = jsonObject.getString("follow");

			valueName.setText(first_name + " " + last_name);
			valueFB.setText(facebook);
			valueInterest.setText(interest);
			valueLocation.setText(location);
			valueOccupation.setText(occupation);
			valueTwitter.setText(twitter);
			valueBirthDay.setText(birthday);
			valueCute.setText(cute);
			valueFollow.setText(follow);

			Log.i(TAG, valueName + " " + valueFB + valueCute + valueFollow
					+ valueInterest + valueLocation + valueOccupation
					+ valueTwitter);
			// }
			// } catch (JSONException e) {
			// Log.i(TAG, e.getMessage());
			//
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		}

	}
}
