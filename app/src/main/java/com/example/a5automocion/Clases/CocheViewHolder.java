package com.example.a5automocion.Clases;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a5automocion.MostrarCocheDatos;
import com.example.a5automocion.MostrarDetallesCoche;
import com.example.a5automocion.R;

import java.util.List;

public class CocheViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public static final String EXTRA_OBJETO_MATRICULA = "es.jorge.cocheViewHolder.objeto_matricula";
    public static final String EXTRA_OBJETO_MARCA_KEY = "es.jorge.cocheViewHolder.objeto_marca_key";
    public TextView txtMatriculaRv;
    public TextView txtModeloRv;
    ListaCochesAdapter lcAdapter;
    public CocheViewHolder(@NonNull View itemView, ListaCochesAdapter lcAdapter) {
        super(itemView);
        txtMatriculaRv = (TextView) itemView.findViewById(R.id.txtMatriculaRV);
        txtModeloRv = (TextView) itemView.findViewById(R.id.txtModeloRV);
        this.lcAdapter = lcAdapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int mPosition = getAdapterPosition();

        List<Coches> coches = this.lcAdapter.getListaCoches();
        List<String> keys = this.lcAdapter.getKeys();
        Coches coches1 = coches.get(mPosition);
        String key = keys.get(mPosition);
        Intent intent = new Intent(lcAdapter.getC(), MostrarCocheDatos.class);

        intent.putExtra(EXTRA_OBJETO_MATRICULA, coches1);
        intent.putExtra(EXTRA_OBJETO_MARCA_KEY, key);
        lcAdapter.getC().startActivity(intent);
    }
}
