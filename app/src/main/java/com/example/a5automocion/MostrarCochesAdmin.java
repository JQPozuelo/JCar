package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.ListaCochesAdapter;
import com.example.a5automocion.Clases.ListaUsuariosAdapter;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MostrarCochesAdmin extends AppCompatActivity {

    private TextView txtEmailMA;
    private List<Coches> coches;
    private ArrayList<String> keys;
    //-----------------------------------------
    private Task<QuerySnapshot> myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mDatabase;
    private TextInputEditText edt_Usuario;
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
            //updateUI(user);
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
        CargarEquipos(new CocheStatus() {
            @Override
            public void cocheIsLoaded(List<Coches> coches, List<String> keys) {
                mAdapter.setListaEquipos(coches);
                mAdapter.setKeys(keys);
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
    }
    public void CargarEquipos( final CocheStatus cocheStatus) {
        String crre = String.valueOf(edt_Usuario.getText());
        Usuario u1 = new Usuario(crre);
        this.myRef = mDatabase.collection("Usuarios").document(u1.getMail()).collection("Coches").get();
        this.myRef.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                coches.clear();
                //Task<QuerySnapshot> future = mDatabase.collection("cities").get();
                //List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                List<String> keys = new ArrayList<String>();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    keys.add(String.valueOf(document.getData()));
                    Coches v = document.toObject(Coches.class);
                    coches.add(v);
                }
                cocheStatus.cocheIsLoaded(coches, keys);
            }

        });
    }
}