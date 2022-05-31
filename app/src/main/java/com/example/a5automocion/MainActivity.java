package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;

public class MainActivity extends AppCompatActivity {

    EditText edt_correo;
    EditText edt_contra;
    String title = "Login";
    Provider basic;
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
        edt_correo = (EditText)findViewById(R.id.edt_correo);
        edt_contra = (EditText) findViewById(R.id.edt_contra);
        mAuth = FirebaseAuth.getInstance();
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hola holita vecinito");*/
        this.setTitle(title);

    }

    public void Registro(View view) {
        String email = String.valueOf(edt_correo.getText());
        String password = String.valueOf(edt_contra.getText());
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("firebasedb", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Se ha creado el usuario correctamente.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, Menu1.class);
                            intent.putExtra("correo", email);
                            startActivity(intent);
                        } else {
                            Log.i("firebasedb", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "No se puedo crear el usuario.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Entrar(View view) {
        String email = String.valueOf(edt_correo.getText());
        String password = String.valueOf(edt_contra.getText());
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("firebasedb", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Acceso correcto.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, Menu1.class);
                            intent.putExtra("correo", email);
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
