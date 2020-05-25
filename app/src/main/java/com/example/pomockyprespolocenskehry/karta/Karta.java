package com.example.pomockyprespolocenskehry.karta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.pomockyprespolocenskehry.R;

/**
 * Vytvorí triedu Karta.
 */
public class Karta extends AppCompatActivity {

    private FrameLayout fl;

    /**
     * Pri vytvorení aktivity sa nastavuje layout, v ktorom sa nachádzajú fragmenty.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta);
        if (savedInstanceState == null ) {
            this.infoOkno();

        }

        this.fl = findViewById(R.id.frameLayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ListKarietFragment()).commitNow();

    }

    /**
     * Vytvorí menu.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_karta, menu);
        return true;
    }

    /**
     * Ak si v menu vyberiete možnosť s ikonou i, zobrazia sa informácie, ako používať danú aktivitu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.infoKarta) {
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
        alert.setMessage("Vyber si kartu, s ktorou budeš následne hrať. Pokiaľ presunieš ruku nad telefón, karta sa otočí zadnou stranou navrch!");
        alert.setPositiveButton("OK", null);
        alert.show();
    }



}
