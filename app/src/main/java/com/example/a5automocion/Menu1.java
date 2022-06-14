package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu1 extends AppCompatActivity {

    private String title = "Menu";
    private TextView txtAuten;
    private Button btCrearV;
    private FirebaseUser user;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getEmail().equals("admin@gmail.com"))
        {
            btCrearV.setVisibility(View.INVISIBLE);
        }
        if(user!= null){
            user.reload();
        }
        else{
            Toast.makeText(Menu1.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Menu1.this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);
        txtAuten = (TextView) findViewById(R.id.txtAuten);
        btCrearV = (Button) findViewById(R.id.bt_CrearC);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtAuten.setText(user.getEmail());
        }
        this.setTitle(title);
    }


    public void CrearCoche(View view) {
        Intent intent = new Intent(this, CrearCoche1.class);
        startActivity(intent);
    }

    public void CerrarSesion(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }

    public void MostrarV(View view) {
        if (user.getEmail().equals("admin@gmail.com"))
        {
            Intent intent = new Intent(this, MostrarCochesAdmin.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MostrarCoche.class);
            startActivity(intent);
        }

    }

    public void Mantemientos(View view) {
        Intent intent = new Intent(this, Mantemientos.class);
        startActivity(intent);
    }
}