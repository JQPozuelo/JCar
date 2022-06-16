package com.example.a5automocion.Clases;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
            holder.txtModeloRv.setText(String.valueOf("Modelo: " + coche_actual.getModelo()));
            //Le indico que el boton que contiene mi cardView que se carga en el recyclerView cambien en funcion del estado recibido
            if (coche_actual.getEstado().equals("Disponible"))
            {
                holder.btMosD.setBackgroundColor(GREEN);
            }else if (coche_actual.getEstado().equals("Recepcionado")){
                holder.btMosD.setBackgroundColor(Color.parseColor("#ffff8800"));
            }else{
                holder.btMosD.setBackgroundColor(RED);
            }
            //Aqui le indico que muestre la imagen en funcion de la marca recibida cuando recibe el objeto coche de la base de datos
            if (coche_actual.getMarca().equals("Audi"))
            {
                holder.imgv.setImageResource(R.drawable.logoaudi);
            }else if (coche_actual.getMarca().equals("Alfa Romeo"))
            {
                holder.imgv.setImageResource(R.drawable.logoalfaromeo);
            }else if (coche_actual.getMarca().equals("BMW"))
            {
                holder.imgv.setImageResource(R.drawable.logobmw);
            }else if (coche_actual.getMarca().equals("Citroen"))
            {
                holder.imgv.setImageResource(R.drawable.logocitroen);
            }else if (coche_actual.getMarca().equals("Dacia"))
            {
                holder.imgv.setImageResource(R.drawable.logodacia);
            }else if (coche_actual.getMarca().equals("Fiat"))
            {
                holder.imgv.setImageResource(R.drawable.logofiat);
            }else if (coche_actual.getMarca().equals("Ford"))
            {
                holder.imgv.setImageResource(R.drawable.logoford);

            }else if (coche_actual.getMarca().equals("Honda"))
            {
                holder.imgv.setImageResource(R.drawable.logohonda);

            }else if (coche_actual.getMarca().equals("Hyundai"))
            {
                holder.imgv.setImageResource(R.drawable.logohyundai);

            }else if (coche_actual.getMarca().equals("Kia"))
            {
                holder.imgv.setImageResource(R.drawable.logokia);

            }else if (coche_actual.getMarca().equals("Mercedes"))
            {
                holder.imgv.setImageResource(R.drawable.logomercedes);

            }else if (coche_actual.getMarca().equals("Mini"))
            {
                holder.imgv.setImageResource(R.drawable.logomini);

            }else if (coche_actual.getMarca().equals("Mitsubishi"))
            {
                holder.imgv.setImageResource(R.drawable.logomitsubishi);

            }else if (coche_actual.getMarca().equals("Nissan"))
            {
                holder.imgv.setImageResource(R.drawable.logonissan);

            }else if (coche_actual.getMarca().equals("Opel"))
            {
                holder.imgv.setImageResource(R.drawable.logoopel);

            }else if (coche_actual.getMarca().equals("Peugeot"))
            {
                holder.imgv.setImageResource(R.drawable.logopeugeot);

            }else if (coche_actual.getMarca().equals("Renault"))
            {
                holder.imgv.setImageResource(R.drawable.logorenault);

            }else if (coche_actual.getMarca().equals("Seat"))
            {
                holder.imgv.setImageResource(R.drawable.logoseat);

            }else if (coche_actual.getMarca().equals("Skoda"))
            {
                holder.imgv.setImageResource(R.drawable.logoskoda);

            }else if (coche_actual.getMarca().equals("Toyota"))
            {
                holder.imgv.setImageResource(R.drawable.logotoyota);

            }else if (coche_actual.getMarca().equals("Volkswagen"))
            {
                holder.imgv.setImageResource(R.drawable.logovolkswagen);

            }
        }
        else{
            Toast.makeText(c, "No se pudo cargar la lista de coches", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (listaCoches != null)
            return listaCoches.size();
        else return 0;
    }
}
