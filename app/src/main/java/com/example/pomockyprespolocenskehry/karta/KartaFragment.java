package com.example.pomockyprespolocenskehry.karta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pomockyprespolocenskehry.R;

/**
 * Vytvorí triedu KartaFragment ktorá dedí po triede Fragment.
 */
public class KartaFragment extends Fragment implements SensorEventListener {

    private ImageView img;
    private final int zad = R.drawable.card;
    private int pred;
    private Context context;
    private SensorManager sensorManager;

    /**
     * Pri vytvorení pohľadu na fragment sa získava kontext aplikácie a nastaví sa obrázok, ktorý sa bude zobrazovať na fragmente.
     * Taktiež sa tu inicializuje senzor manažér, ktorý následne registruje typ používaného senzora v danej aktivite na proximity.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_karta, container, false);
        this.context = myView.getContext();
        this.img = myView.findViewById(R.id.obrazokKarty);
        this.pred = this.getArguments().getInt("obrazok");
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.pred);
        this.img.setImageBitmap(icon);



        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_GAME);




        return myView;
    }

    /**
     * Pri zničení fragmentu sa odregistruje Listener zo senzor manažéra.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.sensorManager.unregisterListener(this);
    }

    /**
     * Metóda zisťuje, či sa niečo nachádza vo vzdialenosťi senzora, ktorú dokáže senzor identifikovať.
     * Ak áno, karta sa obráti spodnou časťou navrch, v opačnom prípade sa zobrazuje horná časť karty.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] < this.sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY).getMaximumRange()) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.zad);
            this.img.setImageBitmap(icon);

        } else {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.pred);
            this.img.setImageBitmap(icon);
        }
    }

    /**
     *
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
