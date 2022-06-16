package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    private Button btMostrar;
    @Override
    public void onStart() {
        super.onStart();
        //  Le indico funcionalidad de visibilidad segun el usuario logueado y que cambie el texto
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getEmail().equals("admin@gmail.com"))
        {
            btCrearV.setVisibility(View.INVISIBLE);
            btMostrar.setText("Vehículos");
        }
        // Si el usuario falla y la aplicacion no detecta el usuario logueado te redirige a la pantalla de logueo
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
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu1);
        txtAuten = (TextView) findViewById(R.id.txtAuten);
        btCrearV = (Button) findViewById(R.id.bt_CrearC);
        btMostrar = (Button) findViewById(R.id.bt_Mostrar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Le indico que mientras el usuario no sea nulo el texto escriba el usuario que esta logueado
        if (user != null)
        {
            txtAuten.setText(user.getEmail());
        }
        this.setTitle(title);
    }
    // Estos botones manejan a donde enviar las pantallas segun la funcion que desee realizar el usuario
    public void CrearCoche(View view) {
        Intent intent = new Intent(this, CrearCoche1.class);
        startActivity(intent);
    }

    public void CerrarSesion(View view) {
        // Este boton cerra sesion en el usuario que esta logueado
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }

    public void MostrarV(View view) {
        // Si la aplicacion detecta que el usuario logueado es administrador este te envia a otro pantalla diferente que siendo un usuario normal
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