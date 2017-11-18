package alugueluguebrasilnovo.alugueluguebrasil.Chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import alugueluguebrasilnovo.alugueluguebrasil.Main.DatabaseUtil;
import alugueluguebrasilnovo.alugueluguebrasil.Main.MainActivity;
import alugueluguebrasilnovo.alugueluguebrasil.R;
import alugueluguebrasilnovo.alugueluguebrasil.objetosUsados.Mensagem;
import de.hdodenhof.circleimageview.CircleImageView;

public class AlugueListaConversa extends AppCompatActivity {

    private RecyclerView listaAnuncio1;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;
    private Query mQueryUsuario;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_lista_conversa);
        ActionBar act = getSupportActionBar();
        act.setTitle("Conversas");
        FirebaseApp.initializeApp(AlugueListaConversa.this);
        DatabaseUtil.getDatabase();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        String CurrentUserId = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("mensagem");
        mDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Chat").child(CurrentUserId);
        mUsersDatabase.keepSynced(true);

        listaAnuncio1 = (RecyclerView) findViewById(R.id.lista_conversas);
        listaAnuncio1.setLayoutManager(new LinearLayoutManager(this));


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Mensagem, MensagemViewHolder> msgRec = new
                FirebaseRecyclerAdapter<Mensagem, MensagemViewHolder>(
                        Mensagem.class,
                        R.layout.card_view_mensagem,
                        MensagemViewHolder.class,
                        mUsersDatabase


                ) {
                    @Override
                    protected void populateViewHolder(final MensagemViewHolder MessegesviewHolder, final Mensagem model, int position) {


                        final String chave_anuncio = getRef(position).getKey();

                                MessegesviewHolder.setNome(model.getNome());
                                MessegesviewHolder.setImagem(model.getImagem(), getApplicationContext());

                                MessegesviewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent telaAnuncio = new Intent(getApplicationContext(), AlugueChatAnuncio.class);
                                        telaAnuncio.putExtra("user_id", chave_anuncio );
                                        startActivity(telaAnuncio);
                                        finish();
                                    }
                                });

                    }

                };
        listaAnuncio1.setAdapter(msgRec);

    }

    public static class MensagemViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public MensagemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setImagem(String context, Context imagem){

            CircleImageView imagem_anuncio = (CircleImageView) mView.findViewById(R.id.imagem_chats);
            Picasso.with(imagem).load(context).placeholder(R.drawable.default_avatar).into(imagem_anuncio);

        }

        public void setNome(String nome){
            TextView desc_anuncio = (TextView) mView.findViewById(R.id.displayNameUser);
            desc_anuncio.setText(nome);
        }

    }



    @Override
    public void onBackPressed() {
        Intent telaAnuncio = new Intent(AlugueListaConversa.this, MainActivity.class);
        startActivity(telaAnuncio);
        finish();

    }
    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(AlugueListaConversa.this, MainActivity.class));
        return true;
    }

}

