package com.example.a5automocion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearCoche extends AppCompatActivity {

    private String title = "Crear";
    private TextView txtCorreoA;
    private TextInputEditText edt_Matricula;
    private TextInputEditText edt_Marca;
    private TextInputEditText edt_Modelo;
    private TextInputEditText edt_Motor;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(CrearCoche.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
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
        txtCorreoA = (TextView) findViewById(R.id.txtCorreoA);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtCorreoA.setText(user.getEmail());
        }
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
                db.collection("Usuarios").document(u1.getMail()).collection("Coches")
                                    .document(c1.getMatricula()).set(c1);
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

}