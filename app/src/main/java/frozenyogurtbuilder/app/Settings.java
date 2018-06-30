package frozenyogurtbuilder.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    //public static String defAppLanguage;
    private Button btn_goTo_chaLa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadLocale();
        setContentView(R.layout.activity_settings);

        //changing ActionBar Title (otherwise language of the system)
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(getResources().getString(R.string.app_name));


        // btn_goTo_chaLa = findViewById(R.id.btn_goTo_chaLa);
        //btn_goTo_chaLa.setOnClickListener(new View.OnClickListener() {


            /*public void onClick(View view) {
                //showChangeLanguageDialog();
            } */
        }



   /* private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "German"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings.this);
        mBuilder.setTitle("Sprache auswählen");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0) {
                    setLocale("en");
                    recreate();
                } else if (i == 1) {
                    setLocale("de");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //saving data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("MyLang", lang);
        editor.apply();

    }
    //loading language in our shared preferences
    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("MyLang", "");
        setLocale(language);

    } */

}
