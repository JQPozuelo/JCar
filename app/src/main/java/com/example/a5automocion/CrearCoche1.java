package com.example.a5automocion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a5automocion.Clases.Coches;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CrearCoche1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private String title = "Crear";
    private TextView txtCorreoA;
    private TextInputEditText edt_Matricula;
    private TextInputEditText edt_Marca;
    private TextInputEditText edt_Modelo;
    private TextInputEditText edt_Motor;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Spinner sp_marca;
    private Spinner sp_modelo;
    private String marcaelegida;
    private String modeloelegido;
    private Task<DocumentSnapshot> myRef;
    private Task<QuerySnapshot> myD;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(CrearCoche1.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CrearCoche1.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_coche1);
        edt_Marca = (TextInputEditText) findViewById(R.id.edt_Marca);
        edt_Matricula = (TextInputEditText) findViewById(R.id.edt_Matricula);
        edt_Modelo = (TextInputEditText) findViewById(R.id.edt_Modelo);
        edt_Motor = (TextInputEditText) findViewById(R.id.edt_Motor);
        txtCorreoA = (TextView) findViewById(R.id.txtCorreoA);
        sp_marca = (Spinner) findViewById(R.id.sp_marca);
        sp_modelo = (Spinner) findViewById(R.id.sp_modelo);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtCorreoA.setText(user.getEmail());
        }
        this.setTitle(title);
        if (sp_marca != null)
        {
            try {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                List<String> marcas = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CrearCoche1.this, R.layout.estilospinner, marcas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_marca.setAdapter(adapter);
                db.collection("Coches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String p = document.getId();
                                marcas.add(p);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                sp_marca.setOnItemSelectedListener(CrearCoche1.this);
            }catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }
    }

    public void GuardarCoche(View view) {
        String matrCoche = String.valueOf(edt_Matricula.getText());
        //String marcCoche = String.valueOf(edt_Marca.getText());
        //String modeCoche = String.valueOf(edt_Modelo.getText());
        String motCoche = String.valueOf(edt_Motor.getText());
        String crre = String.valueOf(txtCorreoA.getText());
        String estado = "Disponible";
        boolean error = false;
        if(matrCoche.isEmpty())
        {
            edt_Matricula.setError("Debes introducir una matricula");
            error = true;
        }
        /*if(marcCoche.isEmpty())
        {
            edt_Marca.setError("Debes introducir una marca");
            error = true;
        }
        if(modeCoche.isEmpty())
        {
            edt_Modelo.setError("Debes introducir un modelo");
            error = true;
        }*/
        if(motCoche.isEmpty())
        {
            edt_Motor.setError("Debes introducir un motor");
            error = true;
        }
        if(error)
        {
            return;
        }

        AlertDialog.Builder alerta1 = new AlertDialog.Builder(CrearCoche1.this);
        alerta1.setTitle("¿Desea guardar el coche?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Coches c1 = new Coches(matrCoche, marcaelegida, modeloelegido, motCoche, estado, crre);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Usuarios").document(user.getEmail()).collection("Coches")
                                    .document(c1.getMatricula()).set(c1);
                Toast.makeText(CrearCoche1.this, "Coche añadido correctamente", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        marcaelegida = adapterView.getItemAtPosition(i).toString();
        if (marcaelegida != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            List<String> modelos = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(CrearCoche1.this, R.layout.estilospinner, modelos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_modelo.setAdapter(adapter);
            myRef = db.collection("Coches").document(marcaelegida).get();
            myRef.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            String p = documentSnapshot.getString("Modelo");
                            String p1 = documentSnapshot.getString("Modelo1");
                            modelos.add(p);
                            modelos.add(p1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            sp_modelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    modeloelegido = sp_modelo.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}