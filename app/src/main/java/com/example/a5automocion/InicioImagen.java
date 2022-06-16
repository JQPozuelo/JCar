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
import android.widget.Toolbar;

public class InicioImagen extends AppCompatActivity {
    private String title = "JCar";
    private ImageView logoImagen;
    private Animation animacion1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inicio_imagen);

        // Agregar Animaciones
        animacion1 = AnimationUtils.loadAnimation(this, R.anim.mover);
        logoImagen = findViewById(R.id.logoJ);
        logoImagen.setAnimation(animacion1);

        // Lanzo el hilo Thread
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InicioImagen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            // Le doy un delay de 2 segundos
        }, 2000);
        this.setTitle(title);
    }
}