package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.LibroMantenimiento;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mantemientos extends AppCompatActivity {
    private String title = "Seguimiento";
    private EditText edt_Mantes;
    private FirebaseAuth mAuth;
    private TextView txtLog;
    private FirebaseUser user;
    private TextInputEditText edt_Matricula;
    private Button btActualizar;
    private Button btBorrar;
    @Override
    public void onStart() {
        super.onStart();
        btActualizar.setVisibility(View.INVISIBLE);
        btBorrar.setVisibility(View.INVISIBLE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser.getEmail().equals("admin@gmail.com"))
        {
            btActualizar.setVisibility(View.VISIBLE);
            btBorrar.setVisibility(View.VISIBLE);
        }
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(Mantemientos.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
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
        edt_Matricula = (TextInputEditText) findViewById(R.id.edt_MatriculaBuscar);
        btActualizar = (Button) findViewById(R.id.btActualizar);
        btBorrar = (Button) findViewById(R.id.btBorrar);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtLog.setText(user.getEmail());
        }
        this.setTitle(title);
    }
    public void BuscarManual(View view) {
        String matricula = String.valueOf(edt_Matricula.getText());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Vehiculos").child(matricula).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String notas = snapshot.child("contenido").getValue().toString();
                    edt_Mantes.setText(notas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        ocultarTeclado();
    }
    public void GuardarManual(View view) {
        String matricula = String.valueOf(edt_Matricula.getText());
        String apuntes = String.valueOf(edt_Mantes.getText());
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(Mantemientos.this);
        alerta1.setTitle("¿Desea guardar el manual?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LibroMantenimiento lb = new LibroMantenimiento(matricula, apuntes);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("Vehiculos").child(lb.getReferencia()).setValue(lb);
                        Toast.makeText(Mantemientos.this, "Manual añadido correctamente", Toast.LENGTH_SHORT).show();
                        edt_Matricula.setText("");
                        edt_Mantes.setText("");
                    }
        });
        alerta1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta1.show();
    }
    public void ActualizarManual(View view) {
        String clave = String.valueOf(edt_Matricula.getText());
        String matricula = String.valueOf(edt_Matricula.getText());
        String datos = String.valueOf(edt_Mantes.getText());
        String prueba = "";
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(Mantemientos.this);
        alerta1.setTitle("¿Desea actualizar el manual?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LibroMantenimiento lbp = new LibroMantenimiento(matricula, datos);
                Map<String, Object> nuevoManual = new HashMap<String,Object>();
                nuevoManual.put(clave,lbp);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Vehiculos").updateChildren(nuevoManual);
                Toast.makeText(Mantemientos.this,"Actualizacion correcta",Toast.LENGTH_LONG).show();
                edt_Matricula.setText("");
                edt_Mantes.setText(prueba);
            }
        });
        alerta1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alerta1.show();
    }
    public void BorrarManual(View view) {
        String clave = String.valueOf(edt_Matricula.getText());
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(Mantemientos.this);
        alerta1.setTitle("¿Desea borrar el manual?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Vehiculos").child(clave).removeValue();
                Toast.makeText(Mantemientos.this,"Borrado correcto ", Toast.LENGTH_LONG).show();
                edt_Matricula.setText("");
                edt_Mantes.setText("");
            }
        });
        alerta1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alerta1.show();
    }
    public void ocultarTeclado(){
        View view = this.getCurrentFocus();
        view.clearFocus();
        if(view!= null)
        {
            InputMethodManager imd = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imd.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}