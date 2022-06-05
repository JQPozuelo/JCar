package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QuerySnapshot;

public class MostrarCochesAdmin extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp_email;
    private TextView txtEmailMA;
    //-----------------------------------------
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(MostrarCochesAdmin.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(MostrarCochesAdmin.this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_coches_admin);
        sp_email = (Spinner) findViewById(R.id.sp_email);
        txtEmailMA = (TextView) findViewById(R.id.txtEmailMA);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtEmailMA.setText(user.getEmail());
        }
        /*myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String spnombre = snapshot.child("")
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }*/

    }
    //public
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}