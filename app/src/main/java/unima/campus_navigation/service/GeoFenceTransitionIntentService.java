package unima.campus_navigation.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
import unima.campus_navigation.activities.IndoorNavigationDetailActivity;
import unima.campus_navigation.activities.MainActivity;

import static unima.campus_navigation.activities.MainActivity.INDOORNAVIGATION_KEY;
import static unima.campus_navigation.activities.MainActivity.ROOM_KEY;

/**
 * Created by Marko on 20.11.16.
 */

public class GeoFenceTransitionIntentService extends IntentService {
    protected static final String TAG = "GeofenceTransitionsIS";
    String roomName;

    public GeoFenceTransitionIntentService() {
        super(TAG);  // use TAG to name the IntentService worker thread
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(INDOORNAVIGATION_KEY, Context.MODE_PRIVATE);
        roomName = sharedPref.getString(ROOM_KEY, null);


        if (event.hasError()) {
            Log.e(TAG, "GeofencingEvent Error: " + event.getErrorCode());
            return;
        }
        String description = getGeofenceTransitionDetails(event);
        sendNotification(description);
    }

    private static String getGeofenceTransitionDetails(GeofencingEvent event) {
        Log.d(TAG,"getGeofenceTransitionDetails");
        String transitionString =
                GeofenceStatusCodes.getStatusCodeString(event.getGeofenceTransition());
        List triggeringIDs = new ArrayList();
        for (Geofence geofence : event.getTriggeringGeofences()) {
            triggeringIDs.add(geofence.getRequestId());
        }
        return String.format("%s: %s", transitionString, TextUtils.join(", ", triggeringIDs));
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts MainActivity.

        Intent notificationIntent = new Intent(getApplicationContext(), IndoorNavigationDetailActivity.class);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(INDOORNAVIGATION_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ROOM_KEY, roomName);
        editor.commit();


        // Get a PendingIntent containing the entire back stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class).addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setColor(Color.RED)
                .setContentTitle("Start Indoornavigation")
                .setSmallIcon(R.drawable.ic_home_black_48dp)
                .setContentText("Welcome at the Entrance, tap to start the indoor navigation")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true);

        // Fire and notify the built Notification.
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }


}
