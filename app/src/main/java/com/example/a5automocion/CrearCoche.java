package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class CrearCoche extends AppCompatActivity {

    String title = "Crear";
    TextView txtCorreoA;
    EditText edt_Matricula;
    EditText edt_Marca;
    EditText edt_Modelo;
    EditText edt_Motor;
    FirebaseFirestore myref;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(CrearCoche.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(CrearCoche.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_coche);
        edt_Marca = (EditText) findViewById(R.id.edt_Marca);
        edt_Matricula = (EditText) findViewById(R.id.edt_Matricula);
        edt_Modelo = (EditText) findViewById(R.id.edt_Modelo);
        edt_Motor = (EditText) findViewById(R.id.edt_Motor);
        mAuth = FirebaseAuth.getInstance();
        //-------------------------------------------------------
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("correo");
        txtCorreoA = (TextView) findViewById(R.id.txtCorreoA);
        txtCorreoA.setText(email);
        this.setTitle(title);

    }

    public void GuardarCoche(View view) {
        String matrCoche = String.valueOf(edt_Matricula.getText());
        String marcCoche = String.valueOf(edt_Marca.getText());
        String modeCoche = String.valueOf(edt_Modelo.getText());
        String motCoche = String.valueOf(edt_Motor.getText());
        String crre = String.valueOf(txtCorreoA.getText());
        //System.out.println(crre);
        Coches c1 = new Coches(matrCoche, marcCoche, modeCoche, motCoche);
        Usuario u1 = new Usuario(crre);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference();
        //DocumentReference documentReference =
        //myRef.child("Usuarios").child(u1.getMail().toString()).child(c1.getMatricula()).setValue(c1);
        db.collection("Usuarios").document(u1.getMail()).collection("Coches")
                .document(c1.getMatricula()).set(c1);
        /*myref.collection("Usuarios").document(u1.getMail()).get();
        myref.collectionGroup("Coches").get();*/
        Toast.makeText(this,"actualizacion correcta",Toast.LENGTH_LONG).show();
    }

}