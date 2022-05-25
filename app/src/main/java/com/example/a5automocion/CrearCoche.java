package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CrearCoche extends AppCompatActivity {
    String title = "Crear";
    TextView txtCorreoA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_coche);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("correo");
        txtCorreoA = (TextView) findViewById(R.id.txtCorreoA);
        txtCorreoA.setText(email);
        this.setTitle(title);
    }
}