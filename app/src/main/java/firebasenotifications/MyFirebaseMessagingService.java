package firebasenotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anagha.astrology.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

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
            String storeId = json.getString("storeId");
            String storeName = json.getString("storeName");
            String saleItemId = json.getString("saleItemId");
            String externalUrl = json.getString("externalurl");
            Intent resultIntent = new Intent();
            switch (screen) {
                case "home":
                    //resultIntent = new Intent(getApplicationContext(), Activitymain.class);
                    break;
                case "storedetail":
                    /*resultIntent = new Intent(getApplicationContext(), UpdatedRoot_Products_List.class);
                    resultIntent.putExtra("shop_name", storeName);
                    resultIntent.putExtra("store_id", storeId);
                    resultIntent.putExtra("call_from", "normal_flow");
                    resultIntent.putExtra("categorie_id", "0");*/
                    break;
                case "productdetail":
                    /*resultIntent = new Intent(getApplicationContext(), UpdatedRoot_Products_List.class);
                    resultIntent.putExtra("shop_name", storeName);
                    resultIntent.putExtra("store_id", storeId);
                    resultIntent.putExtra("call_from", "normal_flow");
                    resultIntent.putExtra("categorie_id", "0");*/
                    break;
                case "publicpage":
                  /*  resultIntent = new Intent(getApplicationContext(), HelpItemsWebViewActivity.class);
                    if (externalUrl != null) {
                        resultIntent.putExtra("help_app_detail", "publicpage");
                        resultIntent.putExtra("publicpage_string", externalUrl);
                    }*/
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
