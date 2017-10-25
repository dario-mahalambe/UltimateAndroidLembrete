package com.infulene.valley.lembrete.backgroud_tasks;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Handler;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.infulene.valley.lembrete.R;


/**
 * Created by user on 24-Oct-17.
 */

public class Lembrete_Receiver extends WakefulBroadcastReceiver {

    Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("BROADCAST","Yeah Working Mothfckr");


       final MediaPlayer mp_lembrete = MediaPlayer.create(context, R.raw.the_weeknd__reminder);
        mp_lembrete.start();

        //After 1s stop the alarm
        // You can adjust the time depending upon your requirement.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mp_lembrete.stop();
            }
        }, 100000);

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                LembreteService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

    }


}
