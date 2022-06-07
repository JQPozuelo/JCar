package com.example.a5automocion;

import static com.example.a5automocion.Clases.CocheViewHolder.EXTRA_OBJETO_MARCA_KEY;
import static com.example.a5automocion.Clases.CocheViewHolder.EXTRA_OBJETO_MATRICULA;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MostrarCocheDatos extends AppCompatActivity {

    private TextInputEditText edt_NombreMatricula;
    private TextInputEditText edt_NombreMarca;
    private TextInputEditText edt_NombreModelo;
    private TextInputEditText edt_NombreMotor;
    private EditText edt_info;
    private TextView txtCorreoMos;
    private TextInputEditText edt_NombreEstado;
    private Coches ce;
    private String key;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    public void onStart() {
        super.onStart();
        edt_NombreEstado.setEnabled(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getEmail().equals("admin@gmail.com"))
        {
            edt_NombreEstado.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_coche_datos);
        edt_NombreMatricula = (TextInputEditText) findViewById(R.id.edt_NombreMatricula);
        edt_NombreMarca = (TextInputEditText) findViewById(R.id.edt_NombreMarca);
        edt_NombreModelo = (TextInputEditText) findViewById(R.id.edt_NombreModelo);
        edt_NombreMotor = (TextInputEditText) findViewById(R.id.edt_NombreMotor);
        edt_info = (EditText) findViewById(R.id.edt_info);
        edt_NombreEstado = (TextInputEditText) findViewById(R.id.edt_NombreMotor);
        txtCorreoMos = (TextView) findViewById(R.id.txtCorreoMos);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtCorreoMos.setText(user.getEmail());
        }
        Intent intent = getIntent();
        if (intent != null) {
            ce = (Coches) intent.getSerializableExtra(EXTRA_OBJETO_MATRICULA);
            key = intent.getStringExtra(EXTRA_OBJETO_MARCA_KEY );
            edt_NombreMatricula.setText(ce.getMatricula());
            edt_NombreMarca.setText(ce.getMarca());
            edt_NombreModelo.setText(ce.getModelo());
            edt_NombreMotor.setText(ce.getMotor());
            edt_NombreEstado.setText(ce.getEstado());
        }
        else{

        }
    }

    public void ElimnarCoche(View view) {
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(MostrarCocheDatos.this);
        alerta1.setTitle("¿Desea borrar el coche?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent = getIntent();
                ce = (Coches) intent.getSerializableExtra(EXTRA_OBJETO_MATRICULA);
                db.collection("Usuarios").document(user.getEmail()).collection("Coches")
                        .document(ce.getMatricula()).delete();
                /*myref.collection("Usuarios").document(u1.getMail()).get();
                myref.collectionGroup("Coches").get();*/
                Toast.makeText(MostrarCocheDatos.this, "Coche borrado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MostrarCocheDatos.this, MostrarCoche.class);
                startActivity(intent1);
                //finish();
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