package frozenyogurtbuilder.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessaging;


public class MenuSettings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.SettingsFragmentStyle);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new Fragment_settings())
                .commit();

        SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
                SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                          String key) {

                        boolean notificationsPref = sharedPreferences.getBoolean("PREF_NOTIFICATIONS", false);

                        if(notificationsPref){
                            FirebaseMessaging.getInstance().subscribeToTopic("PUSH_CHANNEL");

                        }else {
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("PUSH_CHANNEL");

                        }

                    }
                };
    }
}



