package com.example.a5automocion.Clases;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuariosAdapter {
    private Context c;
    private List<Usuario> listaUsuarios;
    private List<String> keys;


    public void setC(Context c){
        this.c = c;
        this.listaUsuarios = new ArrayList<Usuario>();
    }

    public ListaUsuariosAdapter(Context c, List<Usuario> listaUsuarios, List<String> keys) {
        this.c = c;
        this.listaUsuarios = listaUsuarios;
        this.keys = keys;
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

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ListaUsuariosAdapter(Context c) {
        this.c = c;
    }
}
