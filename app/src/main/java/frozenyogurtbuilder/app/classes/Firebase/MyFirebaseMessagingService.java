package frozenyogurtbuilder.app.classes.Firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.RecipeGallery;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private SharedPreferences sharedPref;

    @Override
    public void onCreate(){
        super.onCreate();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        boolean notificationsPref = sharedPref.getBoolean("PREF_NOTIFICATIONS", false);
        if(!notificationsPref){
           return;
        }

        Log.d("Message Received", "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("Message", "Message Notification Body: " + remoteMessage.getNotification().getBody());

            //Create Intent
            Intent myIntent = new Intent(this, RecipeGallery.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);

            //Build Notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.logo_icon)
                    .setColor(4628415)
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            //Show it
            notificationManager.notify(1111, mBuilder.build());
        }

    }

}
