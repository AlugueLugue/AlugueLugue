package alugueluguebrasilnovo.alugueluguebrasil.Anuncio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import alugueluguebrasilnovo.alugueluguebrasil.Main.AlugueMain;
import alugueluguebrasilnovo.alugueluguebrasil.R;

public class AlugueEditarAnuncio extends AppCompatActivity {

    private TextView dataatual;
    private EditText editar_titulo, editar_preco;
    private  EditText editar_cep;
    private EditText editar_desc, editar_endereco, editar_bairro, editar_cidade;
    private Button editar_anuncio;
    private TextView editar_uf;
    private DatabaseReference mEditarAnuncio;
    private DatabaseReference mfire;
    private FirebaseUser mUser;


    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_editar_anuncio);

        final ActionBar act = getSupportActionBar();
        act.setTitle("Editar Anúncio");
        String KeyAnuncio = getIntent().getStringExtra("idAnuncioo");
        String titulo = getIntent().getStringExtra("titulo");
        String descricao = getIntent().getStringExtra("descricao");
        String preco = getIntent().getStringExtra("preco");
        String cep = getIntent().getStringExtra("cep");
        String endereco = getIntent().getStringExtra("endereco");
        String bairro = getIntent().getStringExtra("bairro");
        String cidade = getIntent().getStringExtra("cidade");
        String uf = getIntent().getStringExtra("uf");

        mUser = FirebaseAuth.getInstance().getCurrentUser();


        mEditarAnuncio = FirebaseDatabase.getInstance().getReference().child("Anuncio");
        mfire = mEditarAnuncio.child(KeyAnuncio);



        editar_titulo = (EditText)findViewById(R.id.edttituloeditar);
        editar_desc = (EditText)findViewById(R.id.edtdesceditar);
        editar_endereco = (EditText)findViewById(R.id.edtendeditar);
        editar_bairro = (EditText)findViewById(R.id.edtbairroeditar);
        editar_cidade = (EditText)findViewById(R.id.edtcidadeeditar);
        editar_uf = (TextView) findViewById(R.id.edtufeditar);
        editar_preco = (EditText)findViewById(R.id.edtvaloreditar);
        editar_anuncio = (Button)findViewById(R.id.btn_editar_anuncio);
        editar_cep = (EditText)findViewById(R.id.edtcepeditar);

        editar_titulo.setText(titulo);
        editar_desc.setText(descricao);
        editar_preco.setText(preco);
        editar_endereco.setText(endereco);
        editar_bairro.setText(bairro);
        editar_cidade.setText(cidade);
        editar_cep.setText(cep);
        editar_uf.setText(String.valueOf(uf));



        editar_anuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress = new ProgressDialog(AlugueEditarAnuncio.this);
                mProgress.setTitle("Editando Anúncio...");
                mProgress.setMessage("Aguarde, em instante seu anúncio será editado!");
                mProgress.show();

                String editarTitulo = editar_titulo.getText().toString();
                String editarDescricao = editar_desc.getText().toString();
                String editarEndereco = editar_endereco.getText().toString();
                String editarBairro = editar_bairro.getText().toString();
                String editarCidade = editar_cidade.getText().toString();
                String editarUF = editar_uf.getText().toString();
                String editarPreco = editar_preco.getText().toString();
                String editarCep = editar_cep.getText().toString();

                mfire.child("titulo").setValue(editarTitulo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("descricao").setValue(editarDescricao).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("endereço").setValue(editarEndereco).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("bairro").setValue(editarBairro).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("cidade").setValue(editarCidade).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("estado").setValue(editarUF).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("preco").setValue(editarPreco).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                        }else{
                            mProgress.hide();
                            Toast.makeText(AlugueEditarAnuncio.this,"Erro ao editar anúncio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mfire.child("cep").setValue(editarCep).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                Intent voltar = new Intent(AlugueEditarAnuncio.this, AlugueMain.class);
                startActivity(voltar);
            }

        });

    }


}
