package com.example.pomockyprespolocenskehry.karta;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pomockyprespolocenskehry.R;

/**
 * Vytvára triedu ListKarietFragment, ktorá dedí po Fragmente.
 * Inicializuje jednotlivé ikony na konkrétne pozície do listu prvkov.
 */
public class ListKarietFragment extends Fragment {


    private final int[] listPrvkov = {R.drawable.card1,R.drawable.card2, R.drawable.card3,R.drawable.card4, R.drawable.card5,
            R.drawable.card6, R.drawable.card7,R.drawable.card8, R.drawable.card9,R.drawable.card10, R.drawable.card11,R.drawable.card12,
            R.drawable.card13,R.drawable.card14, R.drawable.card15,R.drawable.card16, R.drawable.card17,R.drawable.card18, R.drawable.card19,
            R.drawable.card20, R.drawable.card21,R.drawable.card22, R.drawable.card23,R.drawable.card24, R.drawable.card25,R.drawable.card26,
            R.drawable.card27,R.drawable.card28, R.drawable.card29,R.drawable.card30,R.drawable.card31, R.drawable.card32};
    private RecyclerView recyclerView;
    private Context context;

    /**
     * Pri vytvorení pohľadu na fragment listu kariet sa zisťuje kontext a vytvára recycler view, teda trieda ListKarietAdapter, ktorá zabezpečuje funkcionalitu listu.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_list_kariet, container, false);

        this.context = myView.getContext();


        this.recyclerView = myView.findViewById(R.id.recyclerViewListKariet);

        this.recyclerView.setLayoutManager(new GridLayoutManager(this.context,3));
        ListKarietAdapter listKarietAdapter = new ListKarietAdapter(this.context,listPrvkov);
        this.recyclerView.setAdapter(listKarietAdapter);



        return myView;


    }
}
