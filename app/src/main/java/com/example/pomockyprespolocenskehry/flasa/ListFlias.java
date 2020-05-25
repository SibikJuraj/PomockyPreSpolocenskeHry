package com.example.pomockyprespolocenskehry.flasa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomockyprespolocenskehry.R;

/**
 *  Vytvorenie triedy ListFlias.
 *  Inicializuje ikony na jednotlivé pozície listuPrvkov.
 */
public class ListFlias extends AppCompatActivity {


    private final int[] listPrvkov = {R.drawable.bottle, R.drawable.bottle1, R.drawable.bottle2, R.drawable.bottle3, R.drawable.bottle4};
    private RecyclerView recyclerView;

    /**
     * Pri vytvorení aktivity sa priradí aktivite RecyclerView, ktorý spravuje list výberu fliaš.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_flias);
        this.recyclerView = findViewById(R.id.recyclerViewListFlias);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        ListFliasAdapter listFliasAdapter = new ListFliasAdapter(this,this.listPrvkov);
        this.recyclerView.setAdapter(listFliasAdapter);

    }

    /**
     * Posiela odpoveď aktivite Flasa, vyberie pozíciu z listuPrvkov na ktorej sa nachádza požadovaná ikona a tú následne odošle.
     * @param view
     * @param position
     */
    public void returnReply(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("obrazok",this.listPrvkov[position]);
        setResult(RESULT_OK,intent);
        finish();
    }

}
