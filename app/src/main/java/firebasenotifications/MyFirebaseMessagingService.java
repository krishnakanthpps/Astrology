package firebasenotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anagha.astrology.Notifications;
import com.anagha.astrology.R;
import com.anagha.astrology.SelectedSignDashBoard;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import dashboard.DailyHoroscope;
import dashboard.Remidies;
import utilitys.Config;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String title;
    String message;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //handleNotification(remoteMessage.getNotification().getBody());
            Log.e(TAG, "From: " + remoteMessage.getFrom());
        }*/
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            if (remoteMessage.getNotification().getTitle() != null) {
                title = remoteMessage.getNotification().getTitle();
            } else {
                title = "SriAstrology Updates";
            }
            if (remoteMessage.getNotification().getBody() != null) {
                message = remoteMessage.getNotification().getBody();
            } else {
                message = "For More Astro Services Open App";
            }
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        try {
            String screen = json.getString("screen");
            Intent resultIntent = new Intent();
            switch (screen) {
                case "home":
                    resultIntent = new Intent(getApplicationContext(), SelectedSignDashBoard.class);
                    break;
                case "daily":
                    resultIntent = new Intent(this, DailyHoroscope.class);
                    resultIntent.putExtra("bhaavaam", "Daily Predictions");
                    resultIntent.putExtra("call_from", "notification");
                    startActivity(resultIntent);
                    break;
                case "remedies":
                    resultIntent = new Intent(this, Remidies.class);
                    //resultIntent.putExtra("bhaavaam", "");
                    resultIntent.putExtra("call_from", "notification");
                    startActivity(resultIntent);
                    break;
                case "notification":
                    resultIntent = new Intent(this, Notifications.class);
                    //resultIntent.putExtra("bhaavaam", "");
                    resultIntent.putExtra("call_from", "notification");
                    startActivity(resultIntent);
                    break;
                default:
                    break;
            }
            resultIntent.putExtra("from_notification_click", "from_fcm_messagingserviceclasss_creen");
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this);
            /*
            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/raw/notification");
            */
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(message);
            mBuilder.setAutoCancel(true);
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            mBuilder.setSmallIcon(R.drawable.astro_white_notification);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.astro_white_notification));
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Config.NOTIFICATION_ID, mBuilder.build());
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
}
