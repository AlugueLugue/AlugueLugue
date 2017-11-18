package alugueluguebrasilnovo.alugueluguebrasil.Anuncio;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.transition.Visibility;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import alugueluguebrasilnovo.alugueluguebrasil.Main.AlugueMain;
import alugueluguebrasilnovo.alugueluguebrasil.Chat.AlugueChatAnuncio;
import alugueluguebrasilnovo.alugueluguebrasil.Main.DatabaseUtil;
import alugueluguebrasilnovo.alugueluguebrasil.Main.MainActivity;
import alugueluguebrasilnovo.alugueluguebrasil.R;

public class AlugueTelaAnuncio extends AppCompatActivity {

    private String mChave_anuncio = null;
    private Button btnChat;
    private ImageButton excluir, editar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseConversa;
    private DatabaseReference mDatabaseUser;
    private ImageView imgm_anuncio, imgm_anuncio1, imgm_anuncio2, imgm_anuncio3, imgm_anuncio4, imgm_anuncio5;
    private TextView titu_anuncio, voltarTela;
    private TextView descr_anuncio, end_anuncio, bairr_anuncio, cidad_anuncio, uff_anuncio, data_anuncio;
    private TextView c_anuncio, titulo, iduser, preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_tela_anuncio);
        final ActionBar act = getSupportActionBar();

        act.hide();
        DatabaseUtil.getDatabase();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("usuario");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Anuncio");
        mDatabaseConversa = FirebaseDatabase.getInstance().getReference();
        mChave_anuncio = getIntent().getExtras().getString("anuncio_id");
        titulo = (TextView)findViewById(R.id.tituloan);
        excluir = (ImageButton)findViewById(R.id.excanuncio);
        editar = (ImageButton)findViewById(R.id.editAnuncio);
        btnChat = (Button)findViewById(R.id.btn_chat_anuncio);
        imgm_anuncio = (ImageView)findViewById(R.id.add_imganuncio);
        voltarTela = (TextView)findViewById(R.id.btVoltar);
        imgm_anuncio1 = (ImageView)findViewById(R.id.add_imganuncio1);
        imgm_anuncio2 = (ImageView)findViewById(R.id.add_imganuncio2);
        imgm_anuncio3 = (ImageView)findViewById(R.id.add_imganuncio3);
        imgm_anuncio4 = (ImageView)findViewById(R.id.add_imganuncio4);
        imgm_anuncio5 = (ImageView)findViewById(R.id.add_imganuncio5);
        titu_anuncio = (TextView)findViewById(R.id.txt_tituloanuncio);
        descr_anuncio = (TextView)findViewById(R.id.txt_descanuncio);
        data_anuncio = (TextView)findViewById(R.id.txt_dataanuncio);
        end_anuncio = (TextView)findViewById(R.id.txt_endanuncio);
        c_anuncio = (TextView)findViewById(R.id.txt_cepanuncio);
        bairr_anuncio = (TextView)findViewById(R.id.txt_bairroanuncio);
        cidad_anuncio = (TextView)findViewById(R.id.txt_cidadeanuncio);
        uff_anuncio = (TextView)findViewById(R.id.txt_ufanuncio);
        iduser = (TextView)findViewById(R.id.iduser);
        preco = (TextView)findViewById(R.id.preco);


        mDatabase.child(mChave_anuncio).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String anuncioId = (String) dataSnapshot.child("idusuario").getValue();
                final String titulo_anuncio = (String) dataSnapshot.child("titulo").getValue();
                final String descricao_anuncio = (String) dataSnapshot.child("descricao").getValue();
                String dt_anuncio = (String) dataSnapshot.child("data").getValue();
                final String endereco_anuncio = (String) dataSnapshot.child("endereço").getValue();
                final String cep_anuncio = (String) dataSnapshot.child("cep").getValue();
                final String bairro_anuncio = (String) dataSnapshot.child("bairro").getValue();
                final String cidade_anuncio = (String) dataSnapshot.child("cidade").getValue();
                final String estado_anuncio = (String) dataSnapshot.child("estado").getValue();
                final String preco_anuncio = (String) dataSnapshot.child("preco").getValue();
                String img_anuncio = (String) dataSnapshot.child("imagem").getValue();
                String img_anuncio1 = (String) dataSnapshot.child("imagem1").getValue();
                String img_anuncio2 = (String) dataSnapshot.child("imagem2").getValue();
                String img_anuncio3 = (String) dataSnapshot.child("imagem3").getValue();
                String img_anuncio4 = (String) dataSnapshot.child("imagem4").getValue();
                String img_anuncio5 = (String) dataSnapshot.child("imagem5").getValue();
                final String key = dataSnapshot.getKey();
                titulo.setText(titulo_anuncio);
                titu_anuncio.setText(titulo_anuncio);
                descr_anuncio.setText(descricao_anuncio);
                data_anuncio.setText(dt_anuncio);
                end_anuncio.setText(endereco_anuncio);
                c_anuncio.setText(cep_anuncio);
                bairr_anuncio.setText(bairro_anuncio);
                cidad_anuncio.setText(cidade_anuncio);
                uff_anuncio.setText(estado_anuncio);
                iduser.setText(anuncioId);
                preco.setText("R$ "+ preco_anuncio + ",00");
                Picasso.with(AlugueTelaAnuncio.this).load(img_anuncio).into(imgm_anuncio);
                Picasso.with(AlugueTelaAnuncio.this).load(img_anuncio1).into(imgm_anuncio1);
                Picasso.with(AlugueTelaAnuncio.this).load(img_anuncio2).into(imgm_anuncio2);
                Picasso.with(AlugueTelaAnuncio.this).load(img_anuncio3).into(imgm_anuncio3);
                Picasso.with(AlugueTelaAnuncio.this).load(img_anuncio4).into(imgm_anuncio4);
                Picasso.with(AlugueTelaAnuncio.this).load(img_anuncio5).into(imgm_anuncio5);



                if (mAuth.getCurrentUser().getUid().equals(anuncioId)) {

                    excluir.setVisibility(View.VISIBLE);
                    editar.setVisibility(View.VISIBLE);
                    btnChat.setVisibility(View.INVISIBLE);


                }
                btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent telaAnuncio = new Intent(getApplicationContext(), AlugueChatAnuncio.class);
                        telaAnuncio.putExtra("user_id", anuncioId);
                        startActivity(telaAnuncio);
                       // finish();
                    }
                });
                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String titulo = titu_anuncio.getText().toString();
                        String desc = descr_anuncio.getText().toString();
                        String pre = preco.getText().toString();
                        String cep = c_anuncio.getText().toString();
                        String ender = end_anuncio.getText().toString();
                        String bairro = bairr_anuncio.getText().toString();
                        String cidade = cidad_anuncio.getText().toString();
                        String ufes = uff_anuncio.getText().toString();

                        Intent editar = new Intent(AlugueTelaAnuncio.this, AlugueEditarAnuncio.class);
                        editar.putExtra("idAnuncioo", key);
                        editar.putExtra("titulo",titulo);
                        editar.putExtra("descricao", desc);
                        editar.putExtra("preco",pre );
                        editar.putExtra("cep", cep);
                        editar.putExtra("endereco", ender);
                        editar.putExtra("bairro", bairro);
                        editar.putExtra("cidade", cidade);
                        editar.putExtra("uf", ufes);
                        startActivity(editar);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


excluir.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        exibirConfirmacao();
    }

});
voltarTela.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent voltar = new Intent(AlugueTelaAnuncio.this, MainActivity.class);
        startActivity(voltar);
    }
});

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AlugueTelaAnuncio.this, MainActivity.class));
        finish();

    }



    public void exibirConfirmacao(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluindo...");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setMessage("Tem certeza que deseja excluir o Anúncio?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase.child(mChave_anuncio).removeValue();
                Intent Anuncio = new Intent(AlugueTelaAnuncio.this, AlugueMain.class);
                startActivity(Anuncio);
                finish();
            }
        });
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta.show();

    }

    public void enviarDados(){





    }
    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(AlugueTelaAnuncio.this, MainActivity.class));
        return true;
    }

}
