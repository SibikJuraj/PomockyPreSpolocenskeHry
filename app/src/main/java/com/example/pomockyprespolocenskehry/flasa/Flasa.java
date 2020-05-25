package com.example.pomockyprespolocenskehry.flasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.pomockyprespolocenskehry.R;

import java.util.Random;

/**
 *  Vytvorenie triedy Flasa.
 *
 */
public class Flasa extends AppCompatActivity {
    private ImageView picture;
    private Random random;
    private float odkial;
    private float pokial;
    private float pridajStupne;
    private int flasaIcon = R.drawable.bottle;
    private int rotaciaDispleja;
    private boolean spinning = false;

    /**
     * Pri vytvorení aktivity sa zisťuje aktuálna rotácia displeja, ku obrázku fľaše sa priradí ID pohľadu obrázka.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flasa);
        this.rotaciaDispleja = getWindowManager().getDefaultDisplay().getRotation();
        this.pridajStupne = 0;
        this.odkial = 0;
        this.random = new Random();
        if (savedInstanceState == null ) {
            this.infoOkno();

        }
        this.picture = findViewById(R.id.imageView2);

    }

    /**
     * Po stlačení fľaše sa vygeneruje konečná pozícia a následne sa fľaša začne rotovať v smere hodinových ručičiek a po určitom čase sa zastaví na konečnej pozícií.
     * Po následnom stlačení sa fľaša začne rotovať od nulovej pozície.
     * Pokiaľ sa fľaša rotuje, nemožno ju stlačiť ani otočiť displej do iného režimu.
     * @param view
     */
    public void rotuj(View view) {
        this.picture.setRotation(0);
        if (!this.spinning) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            this.pokial = this.random.nextInt(800) + 5000;
            RotateAnimation rotate = new RotateAnimation(0,this.pokial,(this.picture.getPivotX()),(this.picture.getPivotY()));
            rotate.setDuration(1500);
            rotate.setFillAfter(false);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    spinning = true;
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                    spinning = false;
                    picture.setVisibility(View.INVISIBLE);
                    nastavRotaciu();
                    System.out.println(odkial);

                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            this.odkial = this.pokial % 360;
            this.picture.startAnimation(rotate);

        }

    }

    /**
     * Ukladá rotáciu fľaše a zároveň pripravuje fľašu na zmenu otočenia fľaše pri rotácií displeja.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.odkial = this.picture.getRotation();
        if(getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_90) {
            this.pridajStupne = 270;
            this.picture.setRotation((this.odkial + pridajStupne) % 360);
            outState.putFloat("rotacia",this.picture.getRotation());
            outState.putFloat("pridajStupne",this.pridajStupne);


        } else if (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_270) {
            this.pridajStupne = 90;
            this.picture.setRotation((this.odkial + pridajStupne) % 360);
            outState.putFloat("rotacia",this.picture.getRotation());
            outState.putFloat("pridajStupne",this.pridajStupne);

        } else if (getWindowManager().getDefaultDisplay().getRotation() == Surface.ROTATION_0) {
            if (this.rotaciaDispleja == Surface.ROTATION_90 ) {
                this.pridajStupne = 90;
                this.picture.setRotation((this.odkial + pridajStupne) % 360);
                outState.putFloat("rotacia",this.picture.getRotation());
                outState.putFloat("pridajStupne",this.pridajStupne);
            } else if (this.rotaciaDispleja == Surface.ROTATION_270 ) {
                this.pridajStupne = 270;
                this.picture.setRotation((this.odkial + pridajStupne) % 360);
                outState.putFloat("rotacia",this.picture.getRotation());
                outState.putFloat("pridajStupne",this.pridajStupne);
            }

        }


        outState.putFloat("odkial",this.odkial);
        outState.putInt("flasaIcon",this.flasaIcon);
    }

    /**
     * Pri obnovení aktivity sa pri možnej rotácií displeja fľaša otočí o požadovaný počet stupňov vďaka atribútu pridajStupne.
     * @param savedInstanceState
     */
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.odkial = savedInstanceState.getFloat("odkial",0);
        this.flasaIcon = savedInstanceState.getInt("flasaIcon",0);
        this.pridajStupne = savedInstanceState.getFloat("pridajStupne",0);
        this.picture.setRotation(savedInstanceState.getFloat("rotacia",this.picture.getRotation()));
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.flasaIcon);
        this.picture.setImageBitmap(icon);
        this.rotaciaDispleja = getWindowManager().getDefaultDisplay().getRotation();

    }

    /**
     * Vytvorí menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flasa, menu);
        return true;
    }

    /**
     * Pri výbere položiek s menu sa zobrazí daná informácia alebo aktivita.
     * Pokiaľ bola kliknutá ikona s písmenom i (informácie), vyšle sa metóda infoOkno().
     * Pri stlačení ikony v tvare fľaše sa otvorí nová aktivita s výberom rôznych typov fliaš.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.infoFlasa:
                this.infoOkno();
                break;
            case R.id.moznostiFlase:
                Intent intent = new Intent(this, ListFlias.class);
                startActivityForResult(intent,1);


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Okno, ktoré informuje používateľa o fungovaní danej aktivity.
     */
    public void infoOkno() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        alert.setMessage("Klikni na fľašu aby sa zatočila!");
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    /**
     * Zisťuje, či bol vybraný nový vzhľad fľaše z aktivity ListFlias, ku ktorej sa je možné dostať pomocou ikony v tvare fľaše.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.flasaIcon = data.getIntExtra("obrazok",R.drawable.bottle);
                Bitmap icon = BitmapFactory.decodeResource(this.getResources(),this.flasaIcon);
                this.picture.setImageBitmap(icon);

            }
        }
    }

    /**
     * Metóda je zavolaná po ukončení rotácie fľaše, nastaví rotáciu obrázka na pozíciu, v ktorej sa skončila rotácia.
     */
    public void nastavRotaciu () {
        this.picture.setRotation(this.odkial);
        this.picture.setVisibility(View.VISIBLE);

    }


}
