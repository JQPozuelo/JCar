package com.example.a5automocion.Clases;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5automocion.R;

import java.util.ArrayList;
import java.util.List;

public class ListaCochesAdapter extends RecyclerView.Adapter<CocheViewHolder>{
    private Context c;
    private List<Coches> listaCoches;
    private List<String> keys;
    private final LayoutInflater mInflater;

    public void setC(Context c){
        this.c = c;
        this.listaCoches = new ArrayList<Coches>();
    }

    public ListaCochesAdapter(Context c, List<Coches> listaCoches, List<String> keys) {
        this.c = c;
        this.listaCoches = listaCoches;
        this.keys = keys;
        mInflater = LayoutInflater.from(c);
    }

    public Context getC() {
        return c;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Coches> getListaCoches() {
        return listaCoches;
    }

    public void setListaEquipos(List<Coches> listaCoches) {
        this.listaCoches = listaCoches;
        notifyDataSetChanged();
    }

    public ListaCochesAdapter(Context c) {
        this.c = c;
        mInflater = LayoutInflater.from(c);
    }
    @NonNull
    @Override
    public CocheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_recyclerview_coche, parent, false);
        return new CocheViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CocheViewHolder holder, int position) {
        if(listaCoches!=null) {
            Coches coche_actual = listaCoches.get(position);
            holder.txtMatriculaRv.setText(String.valueOf("Matricula: " + coche_actual.getMatricula()));
            holder.txtModeloRv.setText(String.valueOf("Marca: " + coche_actual.getMarca()));
            holder.txtModeloRv.setBackgroundColor(Color.RED);
        }
        else{

        }
    }


    @Override
    public int getItemCount() {
        if (listaCoches != null)
            return listaCoches.size();
        else return 0;
    }
}
