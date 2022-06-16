package com.example.a5automocion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.regex.Pattern;

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
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_crear_coche1);
        edt_Matricula = (TextInputEditText) findViewById(R.id.edt_Matricula);
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
            // Le meto en un try catch para mejorar ante una posible falla de referencia sobre algun objeto nulo
            try {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                List<String> marcas = new ArrayList<>();
                // Le indico que el adaptador es un array y le paso el estilo que quiero que se vea cuando se vea lo escrito en el array y cuando este se despliegue
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CrearCoche1.this, R.layout.estilospinner, marcas);
                // le indico al spinner que adaptador debe lanzar cuando se inicie la pantalla
                sp_marca.setAdapter(adapter);
                db.collection("Coches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // Si la llamada es correcta, le indico que me recorra los documentos con un foreach
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Obtengo los id de los documentos que estos los acumulo en una variable tipo string
                                // Este documento en la forma que Firebase lo realizo toma esta llamada al documento y que obtenga el id+
                                // A que recoja todos los id de los documentos como una lista solo teniendo que llamar a un unico documento a nivel de codigo
                                String p = document.getId();
                                marcas.add(p);
                            }
                            // Le indico que mantenga actualizado el spinner
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
        String motCoche = String.valueOf(edt_Motor.getText());
        String crre = String.valueOf(txtCorreoA.getText());
        String estado = "Disponible";
        boolean error = false;
        if(matrCoche.isEmpty())
        {
            edt_Matricula.setError("Debes introducir una matricula");
            error = true;
        }else if(!Pattern.compile("[0-9]").matcher(matrCoche).find())
        {
            edt_Matricula.setError("La matricula debe contener letras, compruebe su matricula");
            error = true;
        }
        if(motCoche.isEmpty())
        {
            edt_Motor.setError("Debes introducir un motor");
            error = true;
        }else if(!Pattern.compile("[0-9]").matcher(motCoche).find())
        {
            edt_Motor.setError("El motor debe contener numeros");
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
                FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                Coches c2 = new Coches(matrCoche, marcaelegida, modeloelegido, motCoche, estado, crre);
                myRef = db1.collection("Usuarios").document(user.getEmail()).collection("Coches").document(c2.getMatricula()).get();
                myRef.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(CrearCoche1.this, "Este coche ya existe en nuestra base de datos", Toast.LENGTH_SHORT).show();
                            }else {
                                Coches c1 = new Coches(matrCoche, marcaelegida, modeloelegido, motCoche, estado, crre);
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("Usuarios").document(user.getEmail()).collection("Coches")
                                        .document(c1.getMatricula()).set(c1);
                                Toast.makeText(CrearCoche1.this, "Coche añadido correctamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                });
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
        // Le digo a la marca la posicion, que esta escogida
        marcaelegida = adapterView.getItemAtPosition(i).toString();
        if (marcaelegida != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            List<String> modelos = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(CrearCoche1.this, R.layout.estilospinner, modelos);
            sp_modelo.setAdapter(adapter);
            //Le paso la posicion de la marca
            myRef = db.collection("Coches").document(marcaelegida).get();
            myRef.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Instancio los documentos y le indico que obtenga los resultados
                        DocumentSnapshot documentSnapshot = task.getResult();
                        // Si el documento existe obten los 6 strings que seran 6 modelos y los añade a la lista
                        if (documentSnapshot.exists()) {
                            String p = documentSnapshot.getString("Modelo");
                            String p1 = documentSnapshot.getString("Modelo1");
                            String p2 = documentSnapshot.getString("Modelo2");
                            String p3 = documentSnapshot.getString("Modelo3");
                            String p4 = documentSnapshot.getString("Modelo4");
                            String p5 = documentSnapshot.getString("Modelo5");
                            modelos.add(p);
                            modelos.add(p1);
                            modelos.add(p2);
                            modelos.add(p3);
                            modelos.add(p4);
                            modelos.add(p5);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
            sp_modelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Aqui recoge la posicion del modelo escogido para poder guardarlo en la variable
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