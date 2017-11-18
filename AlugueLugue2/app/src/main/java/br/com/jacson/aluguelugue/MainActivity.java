package alugueluguebrasilnovo.alugueluguebrasil.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import alugueluguebrasilnovo.alugueluguebrasil.Anuncio.AluguePublicarAnuncio;
import alugueluguebrasilnovo.alugueluguebrasil.Anuncio.AlugueTelaAnuncio;
import alugueluguebrasilnovo.alugueluguebrasil.Chat.AlugueListaConversa;
import alugueluguebrasilnovo.alugueluguebrasil.R;
import alugueluguebrasilnovo.alugueluguebrasil.Usuario.AlugueMinhaConta;
import alugueluguebrasilnovo.alugueluguebrasil.Usuario.AlugueTelaLogin;
import alugueluguebrasilnovo.alugueluguebrasil.objetosUsados.Anuncio;
import de.hdodenhof.circleimageview.CircleImageView;


import static alugueluguebrasilnovo.alugueluguebrasil.R.id.nav_sair;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button btn_anunciar, btn_testarCon;
    private RecyclerView listaAnuncio;
    private ImageView imgConect;
    private CircleImageView imgmenu;
    private TextView nomeMenu, emailMenu;
    private DatabaseReference mDatabase;
    private DatabaseReference mDataUser;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private MenuItem login, sair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseUtil.getDatabase();
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Anuncio");
        mDatabase.keepSynced(true);
        listaAnuncio = (RecyclerView) findViewById(R.id.lista_anuncio);
        // listaAnuncio.setHasFixedSize(true);
        listaAnuncio.setLayoutManager(new LinearLayoutManager(this));
        item();
        carregarAnuncios();
        VerificarConexao();
        preencherMenu();

    }


    @Override
    protected void onStart() {
        super.onStart();
        carregarAnuncios();

    }

public void carregarAnuncios(){
    FirebaseRecyclerAdapter<Anuncio, MainActivity.AnuncioViewHolder> firebaseRecyclerAdapter
            = new FirebaseRecyclerAdapter<Anuncio, MainActivity.AnuncioViewHolder>
            (
                    Anuncio.class,
                    R.layout.anuncio_card,
                    MainActivity.AnuncioViewHolder.class,
                    mDatabase

            ) {

        @Override
        protected void populateViewHolder(MainActivity.AnuncioViewHolder viewHolder,
                                          Anuncio model, int position) {


            final String chave_anuncio = getRef(position).getKey();
            final String desc = getRef(position).getKey();

            viewHolder.setTitulo(model.getTitulo());
            viewHolder.setDescricao(model.getDescricao());
            viewHolder.setData(model.getData());
            viewHolder.setPreco(model.getPreco());
            viewHolder.setImagem(getApplicationContext(), model.getImagem());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user == null) {

                        exibirConfirmacao();

                    }else{
                        Intent telaAnuncio = new Intent(MainActivity.this, AlugueTelaAnuncio.class);
                        telaAnuncio.putExtra("anuncio_id", chave_anuncio);
                        startActivity(telaAnuncio);
                        finish();
                    }
                }
            });
        }
    };

    listaAnuncio.setAdapter(firebaseRecyclerAdapter);
}
    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {

        View mView;


        public AnuncioViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitulo(String titulo) {

            TextView titulo_anuncio = (TextView) mView.findViewById(R.id.titulo_anuncio);
            titulo_anuncio.setText(titulo);

        }

        public void setDescricao(String desc) {
            TextView desc_anuncio = (TextView) mView.findViewById(R.id.desc_anuncio);
            desc_anuncio.setText(desc);
        }

        public void setImagem(Context context, String imagem) {

            ImageView imagem_anuncio = (ImageView) mView.findViewById(R.id.imagem_anuncio);
            Picasso.with(context).load(imagem).placeholder(R.drawable.icon).into(imagem_anuncio);

        }

        public void setData(String data) {

            TextView data_anuncio = (TextView) mView.findViewById(R.id.data_anuncio);
            data_anuncio.setText(data);

        }

        public void setPreco(String preco){
            TextView data_anuncio = (TextView) mView.findViewById(R.id.preco_anuncio);

            data_anuncio.setText("R$ "+preco+",00");
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finishAffinity();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent login = new Intent(MainActivity.this, AlugueTelaLogin.class);
            startActivity(login);
            finish();
        } else if (id == R.id.nav_chat) {

            if (mAuth.getCurrentUser() == null) {
                Intent ite = new Intent(MainActivity.this, AlugueTelaLogin.class);
                startActivity(ite);
                finish();
            }else{
                Intent chat = new Intent(MainActivity.this, AlugueListaConversa.class);
                startActivity(chat);
                finish();
            }


        } else if (id == R.id.nav_inserir_anuncio) {
            if (mAuth.getCurrentUser() != null) {
                Intent anuncio = new Intent(MainActivity.this, AluguePublicarAnuncio.class);
                startActivity(anuncio);
            }else{
                Intent login = new Intent(MainActivity.this, AlugueTelaLogin.class);
                startActivity(login);
                finish();
            }
        } else if (id == R.id.nav_minha_conta) {
            if (mAuth.getCurrentUser() == null) {
                Intent ite = new Intent(MainActivity.this, AlugueTelaLogin.class);
                startActivity(ite);
                finish();
            }else{
                Intent ite = new Intent(MainActivity.this, AlugueMinhaConta.class);
                startActivity(ite);
                finish();
            }

        } else if (id == R.id.nav_termo) {
            termoDeUso();
        } else if (id == nav_sair) {
            mAuth.signOut();
            if (mAuth.getCurrentUser() == null) {
                Intent it = new Intent(MainActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void VerificarConexao() {
        ConnectivityManager on = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = on.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            imgConect.setVisibility(View.INVISIBLE);
            btn_testarCon.setVisibility(View.INVISIBLE);
        } else {
            imgConect.setVisibility(View.VISIBLE);
            btn_testarCon.setVisibility(View.VISIBLE);
            btn_anunciar.setVisibility(View.INVISIBLE);
            listaAnuncio.setVisibility(View.INVISIBLE);
        }
    }
    private void termoDeUso(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View viewAlerta = inflater.inflate(R.layout.termodeuso, null);
        alerta.setView(viewAlerta);

        final TextView alertatitulo = (TextView)viewAlerta.findViewById(R.id.termotitulo);
        final TextView alertatexto = (TextView)viewAlerta.findViewById(R.id.termotexto);

        AlertDialog alertTermo = alerta.create();
        alertTermo.show();
    }
    public void item(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View menu = navigationView.getHeaderView(0);
        emailMenu = (TextView)menu.findViewById(R.id.emailViewmenu);
        nomeMenu = (TextView)menu.findViewById(R.id.nomeViewmenu);
        imgmenu = (CircleImageView)menu.findViewById(R.id.imageViewmenu);
        Menu menuu = navigationView.getMenu();
        login = menuu.findItem(R.id.nav_login);
        sair = menuu.findItem(R.id.nav_sair);
        imgConect = (ImageView) findViewById(R.id.imgConect);
        btn_testarCon = (Button) findViewById(R.id.btn_testarCon);
        btn_anunciar = (Button) findViewById(R.id.btn_anunciar);
        btn_anunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarPublicacao();
            }
        });
        btn_testarCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificandoConexao();
            }
        });
    }
    public void exibirConfirmacao(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Logar para visualizar...");
        alerta.setIcon(android.R.drawable.ic_menu_help);
        alerta.setMessage("Para visualizar o anúncio você precisa estar logado, deseja fazer login agora?");
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent Anuncio = new Intent(MainActivity.this, AlugueTelaLogin.class);
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
    public void enviarPublicacao(){
        if (mAuth.getCurrentUser() != null) {
            Intent anuncio = new Intent(MainActivity.this, AluguePublicarAnuncio.class);
            startActivity(anuncio);
            finish();
        }else{
            Intent login = new Intent(MainActivity.this, AlugueTelaLogin.class);
            startActivity(login);
            finish();
        }
    }

    public void preencherMenu(){

        mDataUser = FirebaseDatabase.getInstance().getReference().child("usuario");

        if (mAuth.getCurrentUser() != null) {

            mDataUser.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String NomeMenu = dataSnapshot.child("nome").getValue().toString();
                    String EmailMenu = dataSnapshot.child("email").getValue().toString();
                    String imgMenu = dataSnapshot.child("imagem").getValue().toString();

                    emailMenu.setText(EmailMenu);
                    nomeMenu.setText(NomeMenu);

                    Picasso.with(MainActivity.this).load(imgMenu).placeholder(R.drawable.default_avatar).into(imgmenu);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            login.setVisible(false);
            sair.setVisible(true);
        }else {
            emailMenu.setText("contato.aluguelugue@gmail.com");
            nomeMenu.setText("AlugueLugue");
            imgmenu.setImageResource(R.drawable.icon);
            login.setVisible(true);
            sair.setVisible(false);
        }
    }

    public void verificandoConexao(){
        ConnectivityManager on = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = on.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Toast.makeText(MainActivity.this, "Conectado com Sucesso!!!", Toast.LENGTH_SHORT).show();
            imgConect.setVisibility(View.INVISIBLE);
            btn_testarCon.setVisibility(View.INVISIBLE);
            listaAnuncio.setVisibility(View.VISIBLE);
            btn_anunciar.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(MainActivity.this, "Falha ao se conectar a internet", Toast.LENGTH_SHORT).show();
            imgConect.setVisibility(View.VISIBLE);
            btn_testarCon.setVisibility(View.VISIBLE);
            btn_anunciar.setVisibility(View.INVISIBLE);
            listaAnuncio.setVisibility(View.INVISIBLE);
        }
    }
}
