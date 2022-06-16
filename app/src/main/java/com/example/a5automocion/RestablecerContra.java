package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RestablecerContra extends AppCompatActivity {

    private TextInputEditText edt_ResCorreo;
    private String correo = "";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_restablecer_contra);
        edt_ResCorreo = (TextInputEditText) findViewById(R.id.edt_Rescorreo);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
    }

    public void RestablecerContra(View view) {
        correo = edt_ResCorreo.getText().toString();
        if (!correo.isEmpty())
        {
            mDialog.setMessage("Espere un momento.....");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            mAuth.setLanguageCode("es");
            mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RestablecerContra.this, "Se ha enviado el correo para restablecer tu contraseña", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        finish();
                    }else {
                        Toast.makeText(RestablecerContra.this, "El correo no existe", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                }
            });
        }else {
           Toast.makeText(this, "No puede dejar el correo  en blanco", Toast.LENGTH_SHORT).show();
        }

    }
}