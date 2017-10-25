package com.infulene.valley.lembrete.backgroud_tasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 24-Oct-17.
 */

public class RingTonePlayService /* extends Service*/ {


  /* private MediaPlayer mp_lembrete;

    private boolean isRunning;

    private int startId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startIDd) {

        Log.e("In the service", "Start command mothrfckr");

        //fetch the extra string value
        String state = intent.getExtras().getString("extra");


        assert state != null;

        switch (state) {
            case "alarm on":
                startIDd = 1;
                break;
            case "alarm off":

                startIDd = 0;
                break;
            default:
                startIDd = 0;
                break;
        }


        if (!this.isRunning && startIDd == 1) {

            mp_lembrete = MediaPlayer.create(this, R.raw.the_weeknd__reminder);
            mp_lembrete.start();

            this.isRunning = true;
            this.startId = 0;

            //Make the notifications parameters

            NotificationManager notityManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            Intent intent_main_activity = new Intent(this.getApplicationContext(),MainActivity.class);

            PendingIntent pendingIntent_main_activity =
                    PendingIntent.getActivity(this, 0,intent_main_activity,0);

            NotificationCompat.Builder notification_popUp = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .setContentIntent(pendingIntent_main_activity)
                    .setContentTitle("Lembrete Notification")
                    .setContentText("Prima para fechar!")
                    .setAutoCancel(true);

            Notification notification = notification_popUp.build();
            notityManager.notify(0,notification);

        } else if (this.isRunning && startIDd == 0) {

            mp_lembrete.stop();
            mp_lembrete.reset();

            this.isRunning= false;
            this.startId = 0;


        } else if (!this.isRunning && startIDd == 0) {

            this.isRunning = false;
            this.startId = 0;

        } else if (this.isRunning && startIDd == 1) {

            this.isRunning = true;
            this.startId = 1;

        } else {

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "on Destroy Called", Toast.LENGTH_SHORT).show();

        this.isRunning = false;
    }*/
}
