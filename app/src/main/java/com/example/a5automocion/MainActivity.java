package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edt_correo;
    private TextInputEditText edt_contra;
    private String title = "Login";
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().signOut();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_correo = (TextInputEditText)findViewById(R.id.edt_correo);
        edt_contra = (TextInputEditText) findViewById(R.id.edt_contra);
        mAuth = FirebaseAuth.getInstance();
        this.setTitle(title);
    }
    public void Registro(View view) {
        Intent intent = new Intent(MainActivity.this, Registro.class);
        startActivity(intent);
    }
    public void Entrar(View view) {
        String email = String.valueOf(edt_correo.getText());
        String password = String.valueOf(edt_contra.getText());
        boolean error = false;
        // Creo las validaciones
        if (email.isEmpty())
        {
            edt_correo.setError("Debes introducir un correo");
            error = true;
        }
        if (password.isEmpty())
        {
            edt_contra.setError("Debes introducir la contraseña");
        }
        if (error)
        {
            return;
        }
        // Con la autenticacion de Firebase lanzo el metodo de loguear al usuario con email y contraseña, comprobando si esta en los usuarios registrado en la aplicacion
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // si la tarea lanzada funciona realiza el registro
                        if (task.isSuccessful()) {
                            Log.i("firebasedb", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Acceso correcto.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Menu1.class);
                            startActivity(intent);
                        } else {
                            Log.i("firebasedb", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Creendenciales incorrectas.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void OlvidarContra(View view) {
        Intent intent = new Intent(this, RestablecerContra.class);
        startActivity(intent);
    }
}
