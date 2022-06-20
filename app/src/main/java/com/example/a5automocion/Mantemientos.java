package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import java.util.regex.Pattern;

public class Mantemientos extends AppCompatActivity {
    private String title = "Libro de mantemiento";
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
            // Si el usuario logueado es administrador mostrara los botones
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
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        boolean error = false;
        //Validaciones de campos en blanco y se le marcan unos patrones de escritura para que se escriba de manera correcta las matriculas
        if (matricula.isEmpty())
        {
            edt_Matricula.setError("No puedes dejar la matricula en blanco");
            error = true;
        }else if(!Pattern.compile("[0-9]").matcher(matricula).find())
        {
            edt_Matricula.setError("La matricula debe contener numeros, compruebe su matricula");
            error = true;
        }
        if (error)
        {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Vehiculos").child(matricula).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Si lee la base de datos de manera correcta, comprobara si existe para poder buscarlo
                if (snapshot.exists())
                {
                    String notas = snapshot.child("contenido").getValue().toString();
                    edt_Mantes.setText(notas);
                    Toast.makeText(Mantemientos.this, "Busqueda realizada correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Mantemientos.this, "No existe esa matricula en nuestra base de datos", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        ocultarTeclado();
    }
    public void ActualizarManual(View view) {
        String clave = String.valueOf(edt_Matricula.getText());
        String matricula = String.valueOf(edt_Matricula.getText());
        String datos = String.valueOf(edt_Mantes.getText());
        boolean error = false;
        if (matricula.isEmpty())
        {
            edt_Matricula.setError("No puedes dejar la matricula en blanco");
            error = true;
        }else if(!Pattern.compile("[0-9]").matcher(matricula).find())
        {
            edt_Matricula.setError("La matricula debe contener numeros, compruebe su matricula");
            error = true;
        }
        if (datos.isEmpty())
        {
            edt_Mantes.setError("No se puede dejar la información en blanco");
        }
        if (error)
        {
            return;
        }
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(Mantemientos.this);
        alerta1.setTitle("¿Desea actualizar el manual?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LibroMantenimiento lbp = new LibroMantenimiento(matricula, datos);
                // Realizo un mapeo del objeto para que este lo pueda identificar en la base de datos y asi poder actualizarlo
                Map<String, Object> nuevoManual = new HashMap<String,Object>();
                nuevoManual.put(clave,lbp);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                //Le digo que actualice el child pasandole un nuevo objeto
                myRef.child("Vehiculos").updateChildren(nuevoManual);
                Toast.makeText(Mantemientos.this,"Actualizacion correcta",Toast.LENGTH_LONG).show();
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
    public void BorrarManual(View view) {
        String clave = String.valueOf(edt_Matricula.getText());
        boolean error = false;
        if (clave.isEmpty())
        {
            edt_Matricula.setError("No puedes dejar la matricula en blanco");
            error = true;
        }else if(!Pattern.compile("[0-9]").matcher(clave).find())
        {
            edt_Matricula.setError("La matricula debe contener numeros, compruebe su matricula");
            error = true;
        }
        if (error)
        {
            return;
        }
        AlertDialog.Builder alerta1 = new AlertDialog.Builder(Mantemientos.this);
        alerta1.setTitle("¿Desea borrar el manual?");
        alerta1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Vehiculos").child(clave).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            //Le paso la matricula como clave para poder borrarlo correctamente
                            myRef.child("Vehiculos").child(clave).removeValue();
                            Toast.makeText(Mantemientos.this,"Borrado correcto", Toast.LENGTH_LONG).show();
                            edt_Matricula.setText("");
                            edt_Mantes.setText("");
                        }else {
                            Toast.makeText(Mantemientos.this,"Esa matricula no se puede borrar no existe en nuestra base de datos", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
    public void ocultarTeclado(){
        // Metodo para poder ocultar el teclado cuando le das a un boton, este metodo limpia el focus del teclado
        View view = this.getCurrentFocus();
        //view.clearFocus();
        if(view!= null)
        {
            //Este metodo arbitra la interaccion de la entrada de datos en la aplicacion
            InputMethodManager imd = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // Aqui le indico que el telcado se oculte cuando un boton es presionado
            imd.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}