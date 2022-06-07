package com.example.a5automocion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearCocheFragment extends DialogFragment {
    private TextView txtCorreoA;
    private TextInputEditText edt_Matricula;
    private TextInputEditText edt_Marca;
    private TextInputEditText edt_Modelo;
    private TextInputEditText edt_Motor;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println(currentUser);
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(getActivity(), "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*if(sp_estado != null)
        {
            String estadoC [] = {"Disponible", "En reparacion"};
            ArrayAdapter<String> adapter =new ArrayAdapter<>(this, R.layout.estilospinner, estadoC);
            sp_estado.setAdapter(adapter);
            sp_estado.setOnItemSelectedListener(this);
        }*/
        //-------------------------------------------------------
        /*Bundle extras = getIntent().getExtras();
        String email = extras.getString("correo");
        txtCorreoA = (TextView) findViewById(R.id.txtCorreoA);
        txtCorreoA.setText(email);*/


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_coche, container, false);
        edt_Marca = (TextInputEditText) v.findViewById(R.id.edt_Marca);
        edt_Matricula = (TextInputEditText) v.findViewById(R.id.edt_Matricula);
        edt_Modelo = (TextInputEditText) v.findViewById(R.id.edt_Modelo);
        edt_Motor = (TextInputEditText) v.findViewById(R.id.edt_Motor);
        txtCorreoA = (TextView) v.findViewById(R.id.txtCorreoA);
        //sp_estado = (Spinner) findViewById(R.id.sp_estado);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtCorreoA.setText(user.getEmail());
        }
        return v;
    }

    public void GuardarCoche1(View view) {

        String matrCoche = String.valueOf(edt_Matricula.getText());
        String marcCoche = String.valueOf(edt_Marca.getText());
        String modeCoche = String.valueOf(edt_Modelo.getText());
        String motCoche = String.valueOf(edt_Motor.getText());
        String crre = String.valueOf(txtCorreoA.getText());
        String estado = "Disponible";
        boolean error = false;
        if(matrCoche.isEmpty())
        {
            edt_Matricula.setError("Debes introducir una matricula");
            error = true;
        }
        if(marcCoche.isEmpty())
        {
            edt_Marca.setError("Debes introducir una marca");
            error = true;
        }
        if(modeCoche.isEmpty())
        {
            edt_Modelo.setError("Debes introducir un modelo");
            error = true;
        }
        if(motCoche.isEmpty())
        {
            edt_Motor.setError("Debes introducir un motor");
            error = true;
        }
        if(error)
        {
            return;
        }

        AlertDialog.Builder alerta1 = new AlertDialog.Builder(getActivity());
        alerta1.setTitle("¿Desea guardar el jugador?");
        alerta1.setPositiveButton("si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Coches c1 = new Coches(matrCoche, marcCoche, modeCoche, motCoche, estado);
                Usuario u1 = new Usuario(crre);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                //FirebaseDatabase database = FirebaseDatabase.getInstance();
                //DatabaseReference myRef = database.getReference();
                //DocumentReference documentReference =
                //myRef.child("Usuarios").child(u1.getMail().toString()).child(c1.getMatricula()).setValue(c1);
                db.collection("Usuarios").document(u1.getMail()).collection("Coches")
                        .document(c1.getMatricula()).set(c1);

                /*myref.collection("Usuarios").document(u1.getMail()).get();
                myref.collectionGroup("Coches").get();*/
                Toast.makeText(getActivity(), "actualizacion correcta", Toast.LENGTH_SHORT).show();

            }
        });
        alerta1.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta1.show();
    }
}