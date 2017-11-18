package alugueluguebrasilnovo.alugueluguebrasil.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.reactivestreams.Publisher;

import alugueluguebrasilnovo.alugueluguebrasil.Anuncio.AlugueTelaAnuncio;
import alugueluguebrasilnovo.alugueluguebrasil.R;
import alugueluguebrasilnovo.alugueluguebrasil.Usuario.AlugueMinhaConta;
import alugueluguebrasilnovo.alugueluguebrasil.objetosUsados.Anuncio;

public class AlugueMain extends AppCompatActivity {

    private RecyclerView listaAnuncio1;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseCurrentUser;
    private Query mQueryUsuario;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_main);
        ActionBar act = getSupportActionBar();
        act.setTitle("Meus Anúncios");
        DatabaseUtil.getDatabase();
        FirebaseApp.initializeApp(AlugueMain.this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Anuncio");

        String CurrentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Anuncio");
        mQueryUsuario = mDatabaseCurrentUser.orderByChild("idusuario").equalTo(CurrentUserId);


        listaAnuncio1 = (RecyclerView) findViewById(R.id.lista_anuncio1);
       // listaAnuncio1.setHasFixedSize(true);
        listaAnuncio1.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Anuncio, AnuncioViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Anuncio, AnuncioViewHolder>
                (
                     Anuncio.class,
                     R.layout.anuncios_usuarios,
                     AnuncioViewHolder.class,
                     mQueryUsuario

                ){

                    @Override
                    protected void populateViewHolder(AnuncioViewHolder viewHolder, Anuncio model, int position) {

                            final String chave_anuncio = getRef(position).getKey();

                            viewHolder.setTitulo(model.getTitulo());
                            viewHolder.setData(model.getData());
                            viewHolder.setDescricao(model.getDescricao());
                            viewHolder.setImagem(getApplicationContext(), model.getImagem());

                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent telaAnuncio = new Intent(AlugueMain.this, AlugueTelaAnuncio.class);
                                    telaAnuncio.putExtra("anuncio_id", chave_anuncio);
                                    startActivity(telaAnuncio);

                                }
                            });
                        }

                };
        listaAnuncio1.setAdapter(firebaseRecyclerAdapter);

    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public AnuncioViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitulo(String titulo){

            TextView titulo_anuncio = (TextView)mView.findViewById(R.id.titulo_anuncio);
            titulo_anuncio.setText(titulo);

        }


        public void setImagem(Context context, String imagem){

            ImageView imagem_anuncio = (ImageView)mView.findViewById(R.id.imagem_anuncio);
            Picasso.with(context).load(imagem).placeholder(R.drawable.icon).into(imagem_anuncio);

        }

        public void setData(String data) {

            TextView data_anuncio = (TextView) mView.findViewById(R.id.data_anuncio);
            data_anuncio.setText(data);

        }
        public void setDescricao(String descricao){
            TextView desc_anuncio = (TextView)mView.findViewById(R.id.desc_anuncio);
            desc_anuncio.setText(descricao);
        }
    }



    @Override
    public void onBackPressed() {
        Intent telaAnuncio = new Intent(AlugueMain.this, AlugueMinhaConta.class);
        startActivity(telaAnuncio);
        finish();

    }
    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(AlugueMain.this, AlugueMinhaConta.class));
        return true;
    }

}
