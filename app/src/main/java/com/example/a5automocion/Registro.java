package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private TextInputEditText edt_Rcorreo;
    private TextInputEditText edt_Ncontra;
    private TextInputEditText edt_CNcontra;
    private FirebaseAuth mAuth;
    private String title = "Registro";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edt_Rcorreo = (TextInputEditText) findViewById(R.id.edt_Rcorreo);
        edt_Ncontra = (TextInputEditText) findViewById(R.id.edt_Ncontra);
        edt_CNcontra = (TextInputEditText) findViewById(R.id.edt_CNcontra);
        mAuth = FirebaseAuth.getInstance();
        this.setTitle(title);
    }
    public void validacion(){
        String correo = String.valueOf(edt_Rcorreo.getText());
        String contraN = String.valueOf(edt_Ncontra.getText());
        String contraCN = String.valueOf(edt_CNcontra.getText());

        if(correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches())
        {
            edt_Rcorreo.setError("Correo invalido");
            return;
        }else {
            edt_Rcorreo.setError(null);
        }

        if (contraN.isEmpty() || contraN.length() < 6)
        {
            edt_Ncontra.setError("La contraseña debe contener 6 caracteres");
        }else if(!Pattern.compile("[0-9]").matcher(contraN).find())
        {
            edt_Ncontra.setError("La contraseña debe contener al menos 1 numero");
            return;
        }else {
            edt_Ncontra.setError(null);
        }

        if (!contraCN.equals(contraN)){
            edt_CNcontra.setError("La contraseña no es la misma");
            return;
        }else {
            RegistroUsuario(correo, contraN);
        }
    }
    public void RegistroUsuario(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("firebasedb", "createUserWithEmail:success");
                            Toast.makeText(Registro.this, "Se ha creado el usuario correctamente.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Registro.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.i("firebasedb", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registro.this, "No se pudo crear el usuario.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void RegistrarN(View view) {

        validacion();
    }

}