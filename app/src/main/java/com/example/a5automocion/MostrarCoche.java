package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.ListaCochesAdapter;
import com.example.a5automocion.Clases.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MostrarCoche extends AppCompatActivity {
    private FirebaseFirestore mDatabase;
    private Task<QuerySnapshot> myRef;
    private List<Coches> coches;
    private ListaCochesAdapter mAdapter;
    private ArrayList<String> keys;
    //-------------------------------------
    RecyclerView rv_Mostrar;
    String title = "Vehiculos";
    TextView txtLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_coche);
        rv_Mostrar = findViewById(R.id.rv_Mostrar);
        mAuth = FirebaseAuth.getInstance();
        mAdapter = new ListaCochesAdapter(this);
        Bundle extras = getIntent().getExtras();
        String email = extras.getString("correo");
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setText(email);
        this.setTitle(title);
        //-------------------------------------------------------
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

        //------------------------------------------------------------
        rv_Mostrar.setAdapter(mAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_Mostrar.setLayoutManager(new LinearLayoutManager(this));
        } else {

        }
        //-----------------------------------------------------------
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(MostrarCoche.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(MostrarCoche.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public interface CocheStatus
    {
        void cocheIsLoaded(List<Coches> coches, List<String> keys);
        void cocheIsAdd();
        void cocheIsUpdate();
        void cocheIsDelete();
    }
    public MostrarCoche() {
        this.mDatabase  = FirebaseFirestore.getInstance();
        this.coches  = new ArrayList<Coches>();
    }
    //-------------------------------------------------------
    public void CargarEquipos(final CocheStatus cocheStatus)
    {
        String crre = String.valueOf(txtLogin.getText());
        Usuario u1 = new Usuario(crre);
        this.myRef = mDatabase.collection("Usuarios").document(u1.getMail()).collection("Coches").get();
        this.myRef.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                coches.clear();
                //Task<QuerySnapshot> future = mDatabase.collection("cities").get();
                //List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                List<String> keys = new ArrayList<String>();
                for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    keys.add(String.valueOf(document.getData()));
                    Coches v = document.toObject(Coches.class);
                    coches.add(v);
                }
                cocheStatus.cocheIsLoaded(coches,keys);
            }

        });
    }
}