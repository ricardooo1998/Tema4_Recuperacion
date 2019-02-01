package com.example.ricar.tema4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MisSeguidores extends AppCompatActivity {
    private TextView result;
    private Button atras;
    private DatabaseReference bbdd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_seguidores);

        result=findViewById(R.id.resultadoMisSeguidores);
        atras=findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bbdd= FirebaseDatabase.getInstance().getReference("Usuarios");
        mAuth = FirebaseAuth.getInstance();

    }
    private void mostrar(){
        FirebaseUser user = mAuth.getCurrentUser();
        Query q=bbdd.orderByKey().equalTo(user.getUid());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    bbdd= bbdd.child(ds.getKey()).child("MisSeguidores");
                    Query q2=bbdd.orderByKey();
                    q2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Seguidor seguidor= ds.getValue(Seguidor.class);
                                Query q3=bbdd.orderByChild("id").equalTo(seguidor.getNombre());
                                q3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            result.setText("");
                                            Seguidor seguidor1=ds.getValue(Seguidor.class);
                                            String name=seguidor1.getNombre();
                                            result.setText(result.getText()+"\n"+name);
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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
