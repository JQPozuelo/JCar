package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Mantemientos extends AppCompatActivity {
    private String title = "Seguimiento";
    private EditText edt_Mantes;
    private FirebaseAuth mAuth;
    private TextView txtLog;
    private FirebaseUser user;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(Mantemientos.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            Intent intent = new Intent(Mantemientos.this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantemientos);
        edt_Mantes = (EditText) findViewById(R.id.edt_Manes);
        txtLog = (TextView) findViewById(R.id.txtLog);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtLog.setText(user.getEmail());
        }
        this.setTitle(title);
    }

}