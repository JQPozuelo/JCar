package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Menu1 extends AppCompatActivity {

    String title = "Menu";
    TextView txtAuten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("correo");
        txtAuten = (TextView) findViewById(R.id.txtAuten);
        txtAuten.setText(email);
        this.setTitle(title);
    }


}