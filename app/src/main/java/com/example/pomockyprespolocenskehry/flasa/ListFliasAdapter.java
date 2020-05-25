package com.example.pomockyprespolocenskehry.flasa;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pomockyprespolocenskehry.R;

/**
 *  Adaptér listu fliaš, ktorý zabezpečuje, aby sa dalo vybrať jednotlivé prvky z listu fľiaš, dedí RecyclerView.
 */
public class ListFliasAdapter extends RecyclerView.Adapter<ListFliasAdapter.ListFliasViewHolder> {

    private Context context;
    private int[] listPrvkov;

    /**
     * Konštruktor v ktorom sa získava kontext aktuálnej aplikácie a zoznam obrázkov jednotlivých fliaš.
     * @param context
     * @param listPrvkov
     */
    public ListFliasAdapter(Context context, int[] listPrvkov) {
        this.context = context;
        this.listPrvkov = listPrvkov;

    }

    /**
     * Pri vytvorení ViewHolderu listu fľiaš ku nemu priradí daný layout jedného prvku.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListFliasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.activity_list_flias_item,parent,false);
        return new ListFliasViewHolder(view);
    }

    /**
     * Metóda, v ktorej si adaptér drží informáciu o jednotlivých prvkoch na jednotlivých pozíciách.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ListFliasViewHolder holder, final int position) {
        holder.imageView.setImageResource(listPrvkov[position]);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListFlias)context).returnReply(v,position);

            }
        });
    }

    /**
     * Vracia dĺžku listu fľiaš.
     * @return
     */
    @Override
    public int getItemCount() {
        return listPrvkov.length;
    }


    /**
     *  ListFliasViewHolder je vnorená trieda Adaptéra listu fliaš, drží si informáciu o jednotlivých prvkoch v liste fliaš.
     */
    public class ListFliasViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private LinearLayout linearLayout;

        /**
         * Konštruktor triedy ViewHolder priraďuje jednotlivým prvkom v Adaptéri listu fliaš ich špecifické ikony.
         * @param itemView
         */
        public ListFliasViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prvoklistuflias);
            linearLayout = itemView.findViewById(R.id.layoutListuFlias);
        }
    }


}
