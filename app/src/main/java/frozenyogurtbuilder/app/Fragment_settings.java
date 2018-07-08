package frozenyogurtbuilder.app;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Fragment_settings extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);
    }
}
