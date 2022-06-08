package com.example.a5automocion;

import static com.example.a5automocion.Clases.CocheViewHolder.EXTRA_OBJETO_MARCA_KEY;
import static com.example.a5automocion.Clases.CocheViewHolder.EXTRA_OBJETO_MATRICULA;
import static com.example.a5automocion.MostrarCochesAdmin.EXTRA_MESSAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView txtRecibo;
    private Button btActu;
    private String title = "Vehiculos";
    @Override
    public void onStart() {
        super.onStart();
        edt_NombreMatricula.setEnabled(false);
        edt_NombreModelo.setEnabled(false);
        edt_NombreMarca.setEnabled(false);
        edt_NombreMotor.setEnabled(false);
        edt_NombreEstado.setEnabled(false);
        txtRecibo.setVisibility(View.INVISIBLE);
        btActu.setVisibility(View.INVISIBLE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getEmail().equals("admin@gmail.com"))
        {
            edt_NombreMatricula.setEnabled(true);
            edt_NombreModelo.setEnabled(true);
            edt_NombreMarca.setEnabled(true);
            edt_NombreMotor.setEnabled(true);
            edt_NombreEstado.setEnabled(true);
            txtRecibo.setVisibility(View.VISIBLE);
            btActu.setVisibility(View.VISIBLE);
        }
        if(user!= null){
            user.reload();
        }
        else{
            Toast.makeText(MostrarCocheDatos.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MostrarCocheDatos.this, MainActivity.class);
            startActivity(intent);
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
        edt_NombreEstado = (TextInputEditText) findViewById(R.id.edt_NombreEstado);
        txtCorreoMos = (TextView) findViewById(R.id.txtCorreoMos);
        txtRecibo = (TextView) findViewById(R.id.txtRecibo);
        btActu = (Button) findViewById(R.id.btActualizarM);
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
            edt_info.setText(ce.getComentarios());
            txtRecibo.setText(ce.getReferencia());
        }
        this.setTitle(title);
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

                Toast.makeText(MostrarCocheDatos.this, "Coche borrado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MostrarCocheDatos.this, MostrarCoche.class);
                startActivity(intent1);
            }
        });
        alerta1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta1.show();
    }

    public void ActualizarCoches(View view) {
        String matrCoche = String.valueOf(edt_NombreMatricula.getText());
        String marcCoche = String.valueOf(edt_NombreMarca.getText());
        String modeCoche = String.valueOf(edt_NombreModelo.getText());
        String motCoche = String.valueOf(edt_NombreMotor.getText());
        String comenta = String.valueOf(edt_info.getText());
        String crre = String.valueOf(txtRecibo.getText());
        String estado = String.valueOf(edt_NombreEstado.getText());
        boolean error = false;
        if(matrCoche.isEmpty())
        {
            edt_NombreMatricula.setError("Debes introducir una matricula");
            error = true;
        }
        if(marcCoche.isEmpty())
        {
            edt_NombreMarca.setError("Debes introducir una marca");
            error = true;
        }
        if(modeCoche.isEmpty())
        {
            edt_NombreModelo.setError("Debes introducir un modelo");
            error = true;
        }
        if(motCoche.isEmpty())
        {
            edt_NombreMotor.setError("Debes introducir un motor");
            error = true;
        }
        if (estado.isEmpty())
        {
            edt_NombreEstado.setError("No puedes dejar este campo en blanco");
        }
        if (comenta.isEmpty())
        {
            edt_info.setError("No puedes dejar este campo en blanco");
        }
        if(error)
        {
            return;
        }
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(MostrarCocheDatos.this);
        alerta1.setTitle("¿Desea actualizar el coche?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Coches c1 = new Coches(matrCoche, marcCoche, modeCoche, motCoche, estado, comenta, crre);
                db.collection("Usuarios").document(crre).collection("Coches")
                        .document(ce.getMatricula()).set(c1);
                Toast.makeText(MostrarCocheDatos.this, "Coche actualizado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MostrarCocheDatos.this, MostrarCochesAdmin.class);
                startActivity(intent1);
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