//package com.beveragebooker.customer_app.notifications;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.os.Build;
//import android.util.Log;
//
//import com.beveragebooker.customer_app.R;
//import com.beveragebooker.customer_app.activities.MainActivity;
//import com.beveragebooker.customer_app.api.RetrofitClient;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import androidx.core.app.NotificationCompat;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = "MyFirebaseMsgService";
//
//    /**
//     * Called when message is received.
//     *
//     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
//     */
//    // [START receive_message] remoteMessage is the message value
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String title = remoteMessage.getNotification().getTitle();
//        String body = remoteMessage.getNotification().getBody();
//        //NotificationOutput.displayNotification(getApplicationContext(), title, body);
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//        }
//    }
//
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//    // [END receive_message]
//
//
//    // [START on_new_token]
//
//    /**
//     * Called if InstanceID token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the InstanceID token
//     * is initially generated so this is where you would retrieve the token.
//     */
//    @Override
//    public void onNewToken(String token) {
//        Log.d(TAG, "Refreshed token: " + token);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        //sendRegistrationToServer(token);
//       // int userID = User.getId();
//        //Log.d(TAG, String.valueOf(userID));
//        Log.d(TAG, "This has been called");
//        //sendRegistrationToServer(token, userID);
//
//    }
//
//    public static void sendRegistrationToServer(String token, String email) {
//
//        Call<ResponseBody> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .addToken(token, email);
//
//        call.enqueue(new Callback<ResponseBody>() {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                if(response.code() ==501){
//                    Log.d(TAG,"The token has been received");
//                }
//                else if (response.code() == 502){
//                    Log.d(TAG, "The token was not received");
//                }
//
//                String msg = String.valueOf(response.code());
//                Log.d(TAG, msg + "failed");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//
//
//            }
//        });
//    }
//
//    private void scheduleJob() {
//        // [START dispatch_job]
////        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
////                .build();
////        WorkManager.getInstance().beginWith(work).enqueue();
//        // [END dispatch_job]
//        Log.d(TAG, "scheduleJob: this is a schedules job");
//    }
//
//    /**
//     * Handle time allotted to BroadcastReceivers.
//     */
//    private void handleNow() {
//        Log.d(TAG, "Short lived task is done.");
//    }
//
//}
