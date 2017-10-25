package com.infulene.valley.lembrete.backgroud_tasks;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.infulene.valley.lembrete.R;
import com.infulene.valley.lembrete.activities.AdicionarLembreteActivity;
import com.infulene.valley.lembrete.activities.MainActivity;

/**
 * Created by user on 25-Oct-17.
 */

public class LembreteService extends IntentService {

    private NotificationManager alarmNotificationManager;

    public LembreteService() {
        super("Lembrete Service");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        sendNotification(AdicionarLembreteActivity.msg_notification);
    }



    private void sendNotification(String msg){

        Log.d("AlarmService", "Preparing to send notification...: " + msg);


        NotificationManager notityManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(),MainActivity.class);

        PendingIntent pendingIntent_main_activity =
                PendingIntent.getActivity(this, 0,intent_main_activity,0);

        NotificationCompat.Builder notification_popUp = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentIntent(pendingIntent_main_activity)
                .setContentTitle("Lembrete App")
                .setContentText(AdicionarLembreteActivity.msg_notification + "Clique para ver o lembrete!.")
                .setAutoCancel(true);

        Notification notification = notification_popUp.build();
        notityManager.notify(0,notification);


    }


}
