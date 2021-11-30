package org.miage.placesearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    // donnee dans le constructeur
    public MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflate du layout de la liste
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_villes, parent, false);
        return new ViewHolder(view);
    }

    // binds les information en textview
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String ville = mData.get(position);
        holder.myTextView.setText(ville);
    }

    // nombre total de ligne (non utilise)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // sauvegarde et recycler view pour le scroll
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.detail);
        }
    }
}