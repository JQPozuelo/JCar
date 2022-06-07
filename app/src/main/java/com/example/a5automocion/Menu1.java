package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu1 extends AppCompatActivity {

    String title = "Menu";
    private TextView txtAuten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);
        txtAuten = (TextView) findViewById(R.id.txtAuten);
        //Bundle extras = getIntent().getExtras();
        //String email = extras.getString("correo");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtAuten.setText(user.getEmail());
        }

        //txtAuten.setText(email);
        this.setTitle(title);
    }


    public void CrearCoche(View view) {
        //String email = String.valueOf(txtAuten.getText());
        Intent intent = new Intent(this, CrearCoche.class);
        //CrearCocheFragment cf = new CrearCocheFragment();
        //cf.show(getSupportFragmentManager(), "Navegar a fragment");
        //intent.putExtra("correo", email);
        startActivity(intent);
    }

    public void CerrarSesion(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }

    public void MostrarV(View view) {
        //String email = String.valueOf(txtAuten.getText());
        Intent intent = new Intent(this, MostrarCoche.class);
        //intent.putExtra("correo", email);
        startActivity(intent);
    }

    public void MostrarCAdmin(View view) {
        Intent intent = new Intent(this, MostrarCochesAdmin.class);
        startActivity(intent);
    }
}