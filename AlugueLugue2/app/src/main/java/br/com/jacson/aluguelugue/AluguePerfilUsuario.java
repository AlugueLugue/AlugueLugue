package alugueluguebrasilnovo.alugueluguebrasil.Usuario;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import alugueluguebrasilnovo.alugueluguebrasil.Main.DatabaseUtil;
import alugueluguebrasilnovo.alugueluguebrasil.R;

public class AluguePerfilUsuario extends AppCompatActivity {

    private TextView nome, sobrenome, email, telefoneUser;
    private ImageView imgperfil;

    private DatabaseReference mPerfilDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_perfil_usuario);
        final ActionBar act = getSupportActionBar();

        DatabaseUtil.getDatabase();
        String keyAnuncio = getIntent().getStringExtra("idUser");
        final String emailUserr = getIntent().getStringExtra("email");

        mPerfilDatabase = FirebaseDatabase.getInstance().getReference().child("usuario").child(keyAnuncio);

        imgperfil = (ImageView)findViewById(R.id.imagemPerfil);
        nome = (TextView)findViewById(R.id.nomePerfil);
        email = (TextView)findViewById(R.id.emailperfil);
        sobrenome = (TextView)findViewById(R.id.sobrePerfil);
        telefoneUser = (TextView)findViewById(R.id.telPerfil);

        mPerfilDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String nomePerfil = dataSnapshot.child("nome").getValue().toString();
                String sobrePerfil = dataSnapshot.child("sobrenome").getValue().toString();
                String telefonePerfil = dataSnapshot.child("telefone").getValue().toString();
                String imagemPerfil = dataSnapshot.child("imagem").getValue().toString();
                act.setTitle(nomePerfil+" "+sobrePerfil);
                nome.setText(nomePerfil);
                sobrenome.setText(sobrePerfil);
                telefoneUser.setText(telefonePerfil);
                Picasso.with(AluguePerfilUsuario.this).load(imagemPerfil).placeholder(R.drawable.default_avatar).into(imgperfil);

                email.setText(emailUserr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
