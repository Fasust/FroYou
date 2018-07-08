package frozenyogurtbuilder.app;

import android.app.Activity;
import android.os.Bundle;


public class MenuSettings extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.SettingsFragmentStyle);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new Fragment_settings())
                .commit();
    }
}



