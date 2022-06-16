package com.example.a5automocion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a5automocion.Clases.Coches;
import com.example.a5automocion.Clases.ListaCochesAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MostrarCoche extends AppCompatActivity {
    private FirebaseFirestore mDatabase;
    private Task<QuerySnapshot> myRef;
    private List<Coches> coches;
    private ListaCochesAdapter mAdapter;
    private ArrayList<String> keys;
    private FirebaseUser user;
    private ProgressDialog mDialog;
    //-------------------------------------
    RecyclerView rv_Mostrar;
    String title = "Vehiculos";
    TextView txtLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_mostrar_coche);
        rv_Mostrar = findViewById(R.id.rv_Mostrar);
        mAuth = FirebaseAuth.getInstance();
        mAdapter = new ListaCochesAdapter(this);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            txtLogin.setText(user.getEmail());
        }
        ;
        this.setTitle(title);
        //Lanzo una pantalla de dialogo
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Cargando Vehiculos");
        // Aqui le indico que no pueda quitarla hasta que realice la accion
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        //-------------------------------------------------------
        CargarEquipos(new CocheStatus() {
            // Le indico que si el coche es leido cargue la lista de coches
            @Override
            public void cocheIsLoaded(List<Coches> coches, List<String> keys) {
                mAdapter.setListaEquipos(coches);
                mAdapter.setKeys(keys);
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

        //------------------------------------------------------------
        // Le indico la posicion de la lista al recycler view
        rv_Mostrar.setAdapter(mAdapter);
        // Le digo la orientacion para que muestre el contenido en el recycler view
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_Mostrar.setLayoutManager(new LinearLayoutManager(this));
        } else {

        }
        //-----------------------------------------------------------
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(MostrarCoche.this, "Tienes que estar logueado.", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
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
        // Le indico con las funciones de Firebase donde tiene que buscar para sacar los vehiculos del usuario referenciado en el documento
        this.myRef = mDatabase.collection("Usuarios").document(user.getEmail()).collection("Coches").get();
        this.myRef.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            // Si la llamada se realiza con exito le indico que recorra los documentos, que convierta el documento a objeto y este la añada a una lista de objetos
            // de coches, notifica el cambio y le pasa la lista al metodo de cargar vehiculos
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                coches.clear();
                List<String> keys = new ArrayList<String>();
                for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    keys.add(String.valueOf(document.getData()));
                    Coches v = document.toObject(Coches.class);
                    coches.add(v);
                }
                mAdapter.notifyDataSetChanged();
                cocheStatus.cocheIsLoaded(coches,keys);
            }
        });
    }
}