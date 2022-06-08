package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class InicioImagen extends AppCompatActivity {
    private String title = "Tus vehiculos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inicio_imagen);
        // Agregar Animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.mover);

        ImageView logoImagen = findViewById(R.id.logoJ);

        logoImagen.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InicioImagen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
        this.setTitle(title);
    }
}