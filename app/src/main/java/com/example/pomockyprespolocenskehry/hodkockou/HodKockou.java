package com.example.pomockyprespolocenskehry.hodkockou;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.os.Vibrator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pomockyprespolocenskehry.R;

/**
 * Vytvorenie triedy HodKockou.
 * Inicializuje ikony na jednotlivé pozície atribútu stranyKocky.
 */
public class HodKockou extends AppCompatActivity implements SensorEventListener {

    private Random random;
    private final int[] stranyKocky = {R.drawable.kocka1, R.drawable.kocka2,R.drawable.kocka3, R.drawable.kocka4,R.drawable.kocka5, R.drawable.kocka6};
    private ImageView img;
    private Button button;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private int aktualna = R.drawable.kocka1;
    private int cislo = 0;
    private boolean prebieha = false;
    private boolean zacalSaShake = false;
    private float acelVal;
    private float acelLast;
    private float shake;

    /**
     * Pri vytvorení aktivity sa nastavia základné atribúty a priradia ID ku jednotlivým atribútom.
     * Taktiež sa tu inicializuje senzor manažér, ktorý následne registruje typ používaného senzora v danej aktivite na akcelerometer a vibrátor.
     * Ďalej sa tu získavajú uložené dáta, pokiaľ už bola aktivita v minulosti spustená.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_kockou);
        this.img = findViewById(R.id.kocka);
        this.random = new Random();
        this.button = findViewById(R.id.novyHod);
        this.button.setVisibility(View.VISIBLE);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager.registerListener(this, this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        this.acelVal = SensorManager.GRAVITY_EARTH;
        this.acelLast = SensorManager.GRAVITY_EARTH;
        this.shake = 0.00f;
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        if (savedInstanceState == null ) {
            this.infoOkno();

        }


        SharedPreferences prefReader = getPreferences(MODE_PRIVATE);

        this.aktualna = prefReader.getInt("img",this.aktualna);
        this.prebieha = prefReader.getBoolean("prebieha",this.prebieha);
        this.cislo = prefReader.getInt("cislo",this.cislo);
        this.zacalSaShake = prefReader.getBoolean("zacalSaShake",this.zacalSaShake);
        this.button.setVisibility(prefReader.getInt("viditelneTl",this.button.getVisibility()));
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.aktualna);
        this.img.setImageBitmap(icon);


    }

    /**
     * Pri obnovení aktivity sa zobrazí tlačidlo, ktoré mohlo byť neviditeľné na základe prebiehajúceho hodu.
     */
    @Override
    protected void onResume() {
        this.button.setVisibility(View.VISIBLE);
        this.prebieha = false;
        this.zacalSaShake = false;
        super.onResume();

    }

    /**
     * Pri odídení z aktivity sa ukladajú dáta cez SharedPreferences.
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("img",this.aktualna);
        editor.putBoolean("prebieha",this.prebieha);
        editor.putBoolean("zacalSaShake",this.zacalSaShake);
        editor.putInt("viditelneTl",this.button.getVisibility());
        editor.putInt("cislo",this.cislo);
        editor.apply();


    }

    /**
     * Pri zničení aktivity sa odregistruje Listener zo senzor manažéra.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.sensorManager.unregisterListener(this);
    }

    /**
     * Tlačidlo na začatie nového hodu, pokiaľ hod neprebieha, začne ho a zneviditeľní tlačidlo.
     * @param view
     */
    public void zahajitNovyHod(View view) {
            if (!this.prebieha) {
                this.prebieha = true;
                this.button.setVisibility(View.INVISIBLE);
            }

    }

    /**
     * Pri zmene akcelerometrového senzora sa zisťuje posledná pozícia senzora z momentálnou pozíciou a pokiaľ je dost vysoká, začne sa hod kockou.
     * Po začatí hodu kockou mobil vibruje, aby dal používateľovi najavo že hod prebieha.
     * Pokiaľ po začatí hodu hodnota poslednej pozície oproti momentálnej pozícií klesne pod určitú hranicu, hod sa ukončí.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        this.acelLast = this.acelVal;
        this.acelVal = (float) Math.sqrt((double)(x*x + y*y + z*z));
        float delta = this.acelVal - this.acelLast;
        this.shake = Math.abs(this.shake * 0.9f + delta);
        if (this.zacalSaShake) {
            this.vibrator.vibrate(19);
        }

        if (this.shake > 6) {
            if (this.prebieha) {
                this.zacalSaShake = true;
                int randomNum = random.nextInt(6) + 1;
                this.cislo = randomNum;
                this.aktualna = stranyKocky[randomNum-1];

                Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.aktualna);
                this.img.setImageBitmap(icon);

            }



        } else if (shake < 1 && zacalSaShake && Math.abs(delta) < 5) {
            this.prebieha = false;
            this.zacalSaShake = false;
            this.button.setVisibility(View.VISIBLE);

            Toast toast = Toast.makeText(this,"Hodil si číslo " + this.cislo,Toast.LENGTH_SHORT);
            toast.show();
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

    /**
     * Vytvorí menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kocka, menu);
        return true;
    }

    /**
     * Ak si v menu vyberiete možnosť s ikonou i, zobrazia sa informácie, ako používať danú aktivitu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.infoKocka) {
            this.infoOkno();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Informácie o používaní aktivity.
     */
    public void infoOkno() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        alert.setMessage("Začni nový hod kockou stlačením tlačidla ZAČAŤ NOVÝ HOD. Následne zatras s telefónom aby si hodil kockou!");
        alert.setPositiveButton("OK", null);
        alert.show();
    }



}
