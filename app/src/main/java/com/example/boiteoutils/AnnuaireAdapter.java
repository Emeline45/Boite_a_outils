package com.example.boiteoutils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnnuaireAdapter extends RecyclerView.Adapter<AnnuaireAdapter.AnnuaireViewHolder>{

    List<Annuaire> listeContact;

    public AnnuaireAdapter (List<Annuaire> listeContact){
        this.listeContact = listeContact;
    }

    @Override
    public AnnuaireViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.annuaire, parent, false);
        AnnuaireViewHolder annuaireViewHolder = new AnnuaireViewHolder(view);
        return annuaireViewHolder;
    }

    @Override
    public void onBindViewHolder(AnnuaireViewHolder holder, int position) {
        Annuaire annuaire = listeContact.get(position);
        holder.nom.setText(annuaire.getName());
        holder.number.setText(annuaire.getNumber());
    }

    @Override
    public int getItemCount() {
        return listeContact.size();
    }

    public static class AnnuaireViewHolder extends RecyclerView.ViewHolder{

        TextView nom;
        TextView number;

        public AnnuaireViewHolder(View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            number = itemView.findViewById(R.id.numero);
        }
    }
}
