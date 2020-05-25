package com.example.pomockyprespolocenskehry;

import android.content.Context;
import android.os.Vibrator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * Vytvorí triedu NotifikaciaService ktorá dedí po triede Service
 */
public class NotifikaciaService extends Service {

    private int pocetSekund;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private Notification notification;
    private Vibrator vibrator;

    /**
     * Pri vytvorení servisu sa inicializuje vibrátor.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    /**
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Po spustení servisu sa získa počet sekúnd z aktivity, ktorá tento servis vytvorila na základe intentu.
     * Následne sa spustí časovač, po ktorého uplynutí vyskočí používateľovi notifikácia.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.pocetSekund = intent.getIntExtra("pocetSekund",0);
        this.notificationIntent = intent;

        /**
         * Časovač ktorý sa nastaví na počet sekúnd, ktoré si používateľ zadal v hlavnej aktivite.
         */
        new CountDownTimer(this.pocetSekund * 1000,1000) {
            /**
             *
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {

            }

            /**
             * Po skončení časovača sa zavolá metóda notifikacia().
             */
            @Override
            public void onFinish() {
                notifikacia();
            }


        }.start();


        stopSelf();
        return START_STICKY;
    }

    /**
     * Vytvorí notifikáciu a zobrazí ju používateľovi.
     * Taktiež spôsobí to, že telefón zavibruje a používateľovi vyskočí Toast s upozornením na notifikáciu.
     */
    public void notifikacia() {
        this.pendingIntent = PendingIntent.getActivity(this,0,this.notificationIntent,0);
        this.builder = new NotificationCompat.Builder(this)
                .setContentTitle("Čas na prípitok!")
                .setContentText("Je čas na prípitok!")
                .setSmallIcon(R.drawable.pripitok)
                .setContentIntent(this.pendingIntent)
                .setOngoing(false);
        this.manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        this.notification = this.builder.build();
        this.manager.notify(1,this.notification);

        this.vibrator.vibrate(500);
        Toast toast = Toast.makeText(this,"Prišla ti notifikácia!",Toast.LENGTH_SHORT);
        toast.show();


    }


}