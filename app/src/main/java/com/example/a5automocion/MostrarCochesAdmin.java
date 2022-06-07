package com.example.a5automocion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MostrarCochesAdmin extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp_email;
    private TextView txtEmailMA;
    private List<Coches> coches;
    private List<Usuario> usuarios;
    private ArrayList<String> keys;
    private String tipoEstado;
    private ListaUsuariosAdapter uAdapter;
    //-----------------------------------------
    private Task<QuerySnapshot> myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mDatabase;
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
        sp_email = (Spinner) findViewById(R.id.sp_email);
        txtEmailMA = (TextView) findViewById(R.id.txtEmailMA);
        mAuth = FirebaseAuth.getInstance();
        uAdapter = new ListaUsuariosAdapter(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtEmailMA.setText(user.getEmail());
        }
        if (sp_email != null)
        {
            cargarUsuarios(new UsuarioStatus() {
                @Override
                public void usuarioIsLoaded(List<Usuario> usuarios, List<String> keys) {
                    uAdapter.setListaUsuarios(usuarios);
                    uAdapter.setKeys(keys);
                }

                @Override
                public void usuarioIsAdd() {

                }

                @Override
                public void usuarioIsUpdate() {

                }

                @Override
                public void usuarioIsDelete() {

                }
            });
        }else{
            System.out.println("No funciona");
        }


    }
    public interface UsuarioStatus
    {
        void usuarioIsLoaded(List<Usuario> usuarios, List<String> keys);
        void usuarioIsAdd();
        void usuarioIsUpdate();
        void usuarioIsDelete();
    }
    public MostrarCochesAdmin() {
        this.mDatabase  = FirebaseFirestore.getInstance();
        this.coches  = new ArrayList<Coches>();
        this.usuarios = new ArrayList<Usuario>();
    }
    public void cargarUsuarios(final UsuarioStatus usuarioStatus)
    {

        this.myRef = mDatabase.collection("Usuarios").get();
                /*.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> keys = new ArrayList<String>();
                            for (DocumentSnapshot document : task.getResult()) {
                                keys.add(String.valueOf(document.getData()));
                                Usuario ui1 = document.toObject(Usuario.class);
                                usuarios.add(ui1);
                            }
                            usuarioStatus.usuarioIsLoaded(usuarios,keys);
                        }
                    }
                });*/
        this.myRef.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> keys = new ArrayList<String>();
                for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    keys.add(String.valueOf(document.getData()));
                    Usuario ui1 = document.toObject(Usuario.class);
                    usuarios.add(ui1);
                }
                usuarioStatus.usuarioIsLoaded(usuarios,keys);
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //tipoEstado = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}