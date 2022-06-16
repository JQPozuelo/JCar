package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.ListaCochesAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MostrarCochesAdmin extends AppCompatActivity {

    private String title = "Vehiculos";
    private TextView txtEmailMA;
    private List<Coches> coches;
    private ArrayList<String> keys;
    private ProgressDialog mDialog;
    //-----------------------------------------
    private Task<DocumentSnapshot> myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mDatabase;
    public TextInputEditText edt_Usuario;
    private ListaCochesAdapter mAdapter;
    private RecyclerView rv_Mostrar;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(MostrarCochesAdmin.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            Intent intent = new Intent(MostrarCochesAdmin.this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_coches_admin);
        txtEmailMA = (TextView) findViewById(R.id.txtEmailMA);
        edt_Usuario = (TextInputEditText) findViewById(R.id.edt_correoUsuario);
        rv_Mostrar = findViewById(R.id.rv_MostrarAdmin);
        mAdapter = new ListaCochesAdapter(this);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtEmailMA.setText(user.getEmail());
        }
        rv_Mostrar.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_Mostrar.setLayoutManager(new LinearLayoutManager(this));
        }
        mDialog = new ProgressDialog(this);
        this.setTitle(title);
    }
    public MostrarCochesAdmin() {
        this.mDatabase  = FirebaseFirestore.getInstance();
        this.coches  = new ArrayList<Coches>();
    }

    public interface CocheStatus
    {
        void cocheIsLoaded(List<Coches> coches, List<String> keys);
        void cocheIsAdd();
        void cocheIsUpdate();
        void cocheIsDelete();
    }

    public void BuscarCocheUsuario(View view) {
        //En la accion de cargar los vehículos del usuario se mostrara un dialogo, que no podra ser quitado por el usuario hasta que no se termine la accion donde haya sido puesto
        mDialog.setMessage("Cargando Vehiculos");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        CargarEquipos(new CocheStatus() {
            @Override
            public void cocheIsLoaded(List<Coches> coches, List<String> keys) {
                //Carga la lista de equipos y sus claves
                mAdapter.setListaEquipos(coches);
                mAdapter.setKeys(keys);
                //Quita el dialogo cuando la accion se haya completado
                mDialog.dismiss();
            }

            @Override
            public void cocheIsAdd() {

            }

            @Override
            public void cocheIsUpdate() {

            }

            @Override
            public void cocheIsDelete() {

            }

        });
        ocultarTeclado();
    }
    public void CargarEquipos( final CocheStatus cocheStatus) {
        String crre = String.valueOf(edt_Usuario.getText());
        boolean error = false;
        if (crre.isEmpty())
        {
            edt_Usuario.setError("No puedes dejar el correo en blanco");
            error = true;
        }
        if (error)
        {
            return;
        }
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        //Primero se le pasa el usuario introducido en el campo de texto y comprobara que existe
        myRef = db1.collection("Usuarios").document(crre).get();
        myRef.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists())
                    {
                        //Si el usuario existe este llamara al documento y mostrara los vehiculos de este usuario
                        mDatabase.collection("Usuarios").document(crre).collection("Coches").get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        coches.clear();
                                        List<String> keys = new ArrayList<String>();
                                        // Para saber cuantos documentos tiene este documento usuario, se recorreran con un foreach y se añadiran a la lista
                                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                            keys.add(String.valueOf(document.getData()));
                                            Coches v = document.toObject(Coches.class);
                                            coches.add(v);
                                        }
                                        cocheStatus.cocheIsLoaded(coches, keys);
                                    }
                                });
                    }else {
                        Toast.makeText(MostrarCochesAdmin.this, "Ese usuario no existe.", Toast.LENGTH_SHORT).show();
                        coches.clear();
                        mAdapter.notifyDataSetChanged();
                        mDialog.dismiss();
                    }
                }
            }
        });
    }
    public void ocultarTeclado(){
        View view = this.getCurrentFocus();
        //view.clearFocus();
        if(view!= null)
        {
            InputMethodManager imd = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imd.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}