package com.example.pomockyprespolocenskehry.karta;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomockyprespolocenskehry.R;

/**
 * Adaptér listu kariet, ktorý zabezpečuje, aby sa dalo vybrať jednotlivé prvky z listu kariet, dedí RecyclerView.
 */
public class ListKarietAdapter extends RecyclerView.Adapter<ListKarietAdapter.ListKarietViewHolder> {

    private Context context;
    private int[] listPrvkov;

    /**
     * Konštruktor v ktorom sa získava kontext aktuálnej aplikácie a zoznam obrázkov jednotlivých kariet.
     * @param context
     * @param listPrvkov
     */
    public ListKarietAdapter(Context context,int[] listPrvkov) {
        this.context = context;
        this.listPrvkov = listPrvkov;

    }

    /**
     * Pri vytvorení ViewHolderu listu kariet ku nemu priradí daný layout jedného prvku.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListKarietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.list_kariet_item,parent,false);
        return new ListKarietViewHolder(view);
    }

    /**
     * Metóda, v ktorej si adaptér drží informáciu o jednotlivých prvkoch na jednotlivých pozíciách.
     * Pokiaľ bola nejaká pozícia stlačená, vytvorí sa fragment triedy KartaFragment, odošle sa mu obrázok ktorý sa má zobraziť vo fragmente a pridá aktuálny fragment na backstack.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ListKarietViewHolder holder, final int position) {
        holder.imageView.setImageResource(listPrvkov[position]);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("obrazok",listPrvkov[position]);
                Karta activity = (Karta) context;
                KartaFragment kartaFragment = new KartaFragment();
                kartaFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, kartaFragment).addToBackStack(null).commit();

            }
        });
    }

    /**
     * Vráti počet prvok v liste kariet
     * @return
     */
    @Override
    public int getItemCount() {
        return listPrvkov.length;
    }


    /**
     * ListKarietViewHolder je vnorená trieda Adaptéra listu kariet, drží si informáciu o jednotlivých prvkoch v liste kariet
     */
    public class ListKarietViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private LinearLayout linearLayout;

        /**
         * Konštruktor triedy ViewHolder priraďuje jednotlivým prvkom v Adaptéri listu kariet ich špecifické ikony
         * @param itemView
         */
        public ListKarietViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prvoklistuobrazok);
            linearLayout = itemView.findViewById(R.id.layoutListu);
        }
    }


}
