package alugueluguebrasilnovo.alugueluguebrasil.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import alugueluguebrasilnovo.alugueluguebrasil.R;

public class AlugueEditarCadastro extends AppCompatActivity {

    private EditText editarnome, editartelefone;
    private Button editarcadastro;

    private DatabaseReference mEditarDatabase;
    private FirebaseUser mCurrent_User;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_editar_cadastro);
        ActionBar act = getSupportActionBar();
        act.setTitle("Editar Cadastro");
        mCurrent_User = FirebaseAuth.getInstance().getCurrentUser();

        String idUser = mCurrent_User.getUid();

        mEditarDatabase = FirebaseDatabase.getInstance().getReference().child("usuario").child(idUser);

        String Nome = getIntent().getStringExtra("nome");
        String Telefone = getIntent().getStringExtra("telefone");

        editarnome = (EditText)findViewById(R.id.nomeUsuarioeditarconta);
        editartelefone =(EditText)findViewById(R.id.telUsuarioeditarconta);

        editarnome.setText(Nome);
        editartelefone.setText(Telefone);
        editarcadastro =(Button)findViewById(R.id.btneditarconta);

        editarcadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(AlugueEditarCadastro.this);
                progressDialog.setTitle("Editanto Usuário...");
                progressDialog.setMessage("Aguarde enquanto editamos seus dados");
                progressDialog.show();
                String nomeUsuario = editarnome.getText().toString();
                String telefoneUsuario = editartelefone.getText().toString();

                mEditarDatabase.child("nome").setValue(nomeUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                        }else{
                            progressDialog.hide();
                            Toast.makeText(AlugueEditarCadastro.this, "Erro ao editar dados, por favor tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mEditarDatabase.child("telefone").setValue(telefoneUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                        }else{
                        progressDialog.hide();
                        Toast.makeText(AlugueEditarCadastro.this, "Erro ao editar dados, por favor tente novamente", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
                Intent voltar = new Intent(AlugueEditarCadastro.this,AlugueMinhaConta.class);
                startActivity(voltar);
            }
        });


    }
}
