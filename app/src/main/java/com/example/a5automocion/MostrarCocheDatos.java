package com.example.a5automocion;

import static com.example.a5automocion.Clases.CocheViewHolder.EXTRA_OBJETO_MARCA_KEY;
import static com.example.a5automocion.Clases.CocheViewHolder.EXTRA_OBJETO_MATRICULA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.a5automocion.Clases.Coches;

public class MostrarCocheDatos extends AppCompatActivity {

    private EditText edt_NombreMatricula;
    private EditText edt_NombreMarca;
    private EditText edt_NombreModelo;
    private EditText edt_NombreMotor;
    private Coches ce;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_coche_datos);
        edt_NombreMatricula = (EditText) findViewById(R.id.edt_NomMa);
        edt_NombreMarca = (EditText) findViewById(R.id.edt_NombreM);
        edt_NombreModelo = (EditText) findViewById(R.id.edt_NomMod);
        edt_NombreMotor = (EditText) findViewById(R.id.edt_NombreMotor);
        Intent intent = getIntent();
        if (intent != null) {
            ce = (Coches) intent.getSerializableExtra(EXTRA_OBJETO_MATRICULA);
            key = intent.getStringExtra(EXTRA_OBJETO_MARCA_KEY );
            edt_NombreMatricula.setText(ce.getMatricula());
            edt_NombreMarca.setText(ce.getMarca());
            edt_NombreModelo.setText(ce.getModelo());
            edt_NombreMotor.setText(ce.getMotor());
        }
        else{

        }
    }
}