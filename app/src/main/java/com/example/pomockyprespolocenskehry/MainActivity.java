package com.example.pomockyprespolocenskehry;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.pomockyprespolocenskehry.hodkockou.HodKockou;
import com.example.pomockyprespolocenskehry.karta.Karta;
import com.example.pomockyprespolocenskehry.flasa.Flasa;

/**
 * Vytvorí triedu MainActivity, ktorá je hlavnou triedou aplikácie.
 */
public class MainActivity extends AppCompatActivity {

    private int pocetSekund;
    private EditText oknoSoSekundami;
    private Intent serviceIntent;

    /**
     * Pri vytvorení sa nastaví layout hlavnej aktivity a priradí EditText atribútu oknoSoSekundami príslušné ID.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.pocetSekund = 0;
        this.oknoSoSekundami = findViewById(R.id.nastavPocetSekund);
    }

    /**
     * Po stlačení ikony kociek sa začne nová aktivita triedy HodKockou.
     * @param view
     */
    public void spustiHodKockou(View view) {
        Intent intent = new Intent(this, HodKockou.class);
        startActivity(intent);
    }

    /**
     * Po stlačení ikony karty sa začne nová aktivita triedy Karta.
     * @param view
     */
    public void spustiKartu(View view) {
        Intent intent = new Intent(this, Karta.class);
        startActivity(intent);
    }

    /**
     * Po stlačení ikony fľaše sa začne nová aktivita triedy Fľaša.
     * @param view
     */
    public void spustiFlasu(View view) {
        Intent intent = new Intent(this, Flasa.class);
        startActivity(intent);
    }

    /**
     * Po stlačení ikony dvoch pohárov sa začne nový servis triedy NotifikaciaService.
     * Zisťuje číslo, ktoré používateľ zadal do poľa, ktoré sa nachádza nad ikonou a následne ho pošle servisu.
     * Pokiaľ je číslo privysoké, poprípade neexistujúce, vyhodí toast aby dalo používateľovi najavo, že sa servis nezačal.
     * Ak je náhodou číslo vyššie ako milión, nastaví ho na milión.
     * Každý klik zaregistruje novú notifikáciu a notifikácia sa nezobrazí, pokiaľ bola aplikácia zničená.
     * @param view
     */
    public void spustiCasovac(View view) {
        try {
            this.pocetSekund = Integer.parseInt(this.oknoSoSekundami.getText().toString());
            Toast toast = Toast.makeText(this,"Zapol si časovač na prípitok!",Toast.LENGTH_SHORT);
            toast.show();
            if (this.pocetSekund > 1000000) {
                this.pocetSekund = 1000000;
                this.oknoSoSekundami.setText(String.valueOf(this.pocetSekund));
            }
            this.serviceIntent = new Intent(this, NotifikaciaService.class);
            this.serviceIntent.putExtra("pocetSekund",this.pocetSekund);
            startService(this.serviceIntent);
        } catch (NumberFormatException ex) {
            Toast toast = Toast.makeText(this,"Nezadal si číslo alebo zadané číslo je príliš dlhé!",Toast.LENGTH_SHORT);
            toast.show();

        }


    }

}
