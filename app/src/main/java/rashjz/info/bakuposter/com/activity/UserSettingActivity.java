package rashjz.info.bakuposter.com.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import rashjz.info.bakuposter.com.R;


public class UserSettingActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}
