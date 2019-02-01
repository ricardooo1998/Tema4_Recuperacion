package com.example.ricar.tema4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricar.tema4.Model.Producto;
import com.example.ricar.tema4.Model.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Seguir extends AppCompatActivity implements View.OnClickListener{
    private Usuarios usuarios;
    private Seguidor seguidor;
    private DatabaseReference bbdd,bbdd2;
    private EditText nombre;
    private TextView result;
    private Button cancelar;
    private Button buscar;
    private Button aceptar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguir);



        bbdd= FirebaseDatabase.getInstance().getReference("Usuarios");
        mAuth = FirebaseAuth.getInstance();

        nombre=findViewById(R.id.nombreUsuarioASeguir);
        result=findViewById(R.id.resultSeguidor);
        buscar=findViewById(R.id.Buscar);
        aceptar=findViewById(R.id.Añadir);
        cancelar=findViewById(R.id.Cancelar);

        cancelar.setOnClickListener(this);
        buscar.setOnClickListener(this);
        aceptar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.Cancelar){
            finish();
        }
        if (v.getId()==R.id.Buscar){
            result.setText("");
            String nombreUsuario= String.valueOf(nombre.getText());
            seguidor = new Seguidor(nombreUsuario);
            Query q=bbdd.orderByChild("id").equalTo(nombreUsuario);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){

                        Persona p = ds.getValue(Persona.class);
                        String name=p.getId()+" "+p.getNom()+" "+p.getAp()+" "+p.getEmail();
                        result.setText(name);
                        if (result.getText().equals("")){
                            Toast.makeText(getApplicationContext(),"No se ha encontrado nada",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if (v.getId()==R.id.Añadir){
            String usuario= String.valueOf(nombre.getText());
            seguidor = new Seguidor(usuario);
            if (!result.getText().equals("")) {
                Query q = bbdd.orderByChild("id").equalTo(usuario);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String key = ds.getKey();
                            String keyuser = user.getUid();
                            bbdd2 = FirebaseDatabase.getInstance().getReference("Usuarios");
                            Query q2=bbdd2.orderByKey().equalTo(keyuser);
                            q2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        bbdd2.child(ds.getKey()).child("id").child(key).setValue(seguidor);
                                        Toast.makeText(getApplicationContext(),"Añadido correctamente",Toast.LENGTH_LONG).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"No se ha encontrado nada",Toast.LENGTH_LONG).show();
            }
        }

    }
}
