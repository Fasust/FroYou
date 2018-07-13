package frozenyogurtbuilder.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class Fragment_settings extends PreferenceFragment {

    private SharedPreferences.OnSharedPreferenceChangeListener spChanged;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);

        spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                  String key) {

                boolean notificationsPref = sharedPreferences.getBoolean("PREF_NOTIFICATIONS", false);
                Log.d("Pref: ",key);

                if(notificationsPref){
                    FirebaseMessaging.getInstance().subscribeToTopic("PUSH_CHANNEL");
                    Log.d("Pref:","Noti sub");

                }else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("PUSH_CHANNEL");
                    Log.d("Pref:","Noti unsub");

                }

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(spChanged);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(spChanged);
        super.onPause();
    }
}
