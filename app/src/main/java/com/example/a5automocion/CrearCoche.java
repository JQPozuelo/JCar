package com.example.a5automocion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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
    private TextInputEditText edt_Matricula;
    private TextInputEditText edt_Marca;
    private TextInputEditText edt_Modelo;
    private TextInputEditText edt_Motor;
    //private Spinner sp_estado;
    //FirebaseFirestore myref;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    //---------------------
    //private String tipoEstado;
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
        edt_Marca = (TextInputEditText) findViewById(R.id.edt_Marca);
        edt_Matricula = (TextInputEditText) findViewById(R.id.edt_Matricula);
        edt_Modelo = (TextInputEditText) findViewById(R.id.edt_Modelo);
        edt_Motor = (TextInputEditText) findViewById(R.id.edt_Motor);
        //sp_estado = (Spinner) findViewById(R.id.sp_estado);
        txtCorreoA = (TextView) findViewById(R.id.txtCorreoA);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtCorreoA.setText(user.getEmail());
        }
        /*if(sp_estado != null)
        {
            String estadoC [] = {"Disponible", "En reparacion"};
            ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.estilospinner, estadoC);
            sp_estado.setAdapter(adapter);
            sp_estado.setOnItemSelectedListener(this);
        }*/
        //-------------------------------------------------------
        //Bundle extras = getIntent().getExtras();
        //String email = extras.getString("correo");

        //txtCorreoA.setText(email);
        this.setTitle(title);


    }

    public void GuardarCoche(View view) {
        String matrCoche = String.valueOf(edt_Matricula.getText());
        String marcCoche = String.valueOf(edt_Marca.getText());
        String modeCoche = String.valueOf(edt_Modelo.getText());
        String motCoche = String.valueOf(edt_Motor.getText());
        String crre = String.valueOf(txtCorreoA.getText());
        String estado = "Disponible";
        boolean error = false;
        if(matrCoche.isEmpty())
        {
            edt_Matricula.setError("Debes introducir una matricula");
            error = true;
        }
        if(marcCoche.isEmpty())
        {
            edt_Marca.setError("Debes introducir una marca");
            error = true;
        }
        if(modeCoche.isEmpty())
        {
            edt_Modelo.setError("Debes introducir un modelo");
            error = true;
        }
        if(motCoche.isEmpty())
        {
            edt_Motor.setError("Debes introducir un motor");
            error = true;
        }
        if(error)
        {
            return;
        }

        AlertDialog.Builder alerta1 = new AlertDialog.Builder(CrearCoche.this);
        alerta1.setTitle("¿Desea guardar el coche?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Coches c1 = new Coches(matrCoche, marcCoche, modeCoche, motCoche, estado);
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
                Toast.makeText(CrearCoche.this, "Coche añadido correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alerta1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta1.show();

    }

    /*@Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tipoEstado = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}