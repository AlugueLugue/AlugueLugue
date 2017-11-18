package alugueluguebrasilnovo.alugueluguebrasil.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import alugueluguebrasilnovo.alugueluguebrasil.Main.MainActivity;
import alugueluguebrasilnovo.alugueluguebrasil.R;


public class AlugueTelaCadUsuario extends AppCompatActivity {
    private EditText edtNome, edtSobNome, edtCpf, edtEmail, edtSenha, edtTel, edtSenhaConf;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgessdialog;
    private DatabaseReference mDatabase;
    RadioGroup rdGroupCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_tela_cad_usuario);
        ActionBar act = getSupportActionBar();
        act.setDisplayHomeAsUpEnabled(true);
        act.setTitle("Pessoa Fisica");
        mProgessdialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        edtNome = (EditText) findViewById(R.id.nomeUsuario);
        edtSobNome = (EditText) findViewById(R.id.sobrenomeUsuario);
        edtCpf = (EditText) findViewById(R.id.cpfUsuario);
        edtEmail = (EditText) findViewById(R.id.emailUsuario);
        edtSenha = (EditText) findViewById(R.id.senhaUsuario);
        edtSenhaConf = (EditText) findViewById(R.id.senhaUsuarioConfir);
        edtTel = (EditText) findViewById(R.id.telUsuario);

        rdGroupCad = (RadioGroup) findViewById(R.id.rGroupCad);
        rdGroupCad.check(R.id.rbCpf);

        rdGroupCad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbCnpj) {
                    Intent intent = new Intent(AlugueTelaCadUsuario.this, AlugueCadCnpj.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    finish();
                }

            }
        });
    }

    public void cadastroPF(View v) {
        if (v.getId() == R.id.btnUserPF) {
            String nome = edtNome.getText().toString();
            String sobrenome = edtSobNome.getText().toString();
            String cpf = edtCpf.getText().toString();
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            String telefone = edtTel.getText().toString();
            String tipo = "fisica";
            if (!TextUtils.isEmpty(nome) || !TextUtils.isEmpty(sobrenome)
                    || !TextUtils.isEmpty(cpf) || !TextUtils.isEmpty(email) ||
                    !TextUtils.isEmpty(senha) || !TextUtils.isEmpty(telefone)) {

                mProgessdialog.setTitle("Salvando Usuario...");
                mProgessdialog.setMessage("Aguarde enquanto criamos sua conta!");
                mProgessdialog.setCanceledOnTouchOutside(false);
                mProgessdialog.show();
                register_user(nome, sobrenome, cpf, email, senha, tipo, telefone);
            }
        }

        if (edtSenha.getText().length() < 6) {
            Toast.makeText(this, "Sua senha precisa conter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
        }


    }

    private void register_user(final String nome, final String sobrenome, final String cpf, final String email, final String senha, final String tipo, final String telefone) {
        if (edtSenha.getText().toString().contentEquals(edtSenhaConf.getText().toString())) {
            if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(sobrenome)
                    && !TextUtils.isEmpty(cpf) && !TextUtils.isEmpty(email)
                    && !TextUtils.isEmpty(senha) && !TextUtils.isEmpty(telefone)) {

                mAuth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = current_user.getUid();


                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("usuario").child(uid);

                                    String device_token = FirebaseInstanceId.getInstance().getToken();

                                    HashMap<String, String> userMap = new HashMap<>();
                                    userMap.put("nome", nome);
                                    userMap.put("sobrenome", sobrenome);
                                    userMap.put("cpf", cpf);
                                    userMap.put("telefone", telefone);
                                    userMap.put("tipo", tipo);
                                    userMap.put("email", email);
                                    userMap.put("imagem", "default");
                                    userMap.put("device_token", device_token);

                                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                mProgessdialog.dismiss();
                                                Intent telaInicial = new Intent(AlugueTelaCadUsuario.this, MainActivity.class);
                                                telaInicial.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(telaInicial);
                                                finish();
                                            }

                                        }
                                    });

                                } else {
                                    mProgessdialog.hide();
                                    Toast.makeText(AlugueTelaCadUsuario.this, "" +
                                            "Falha ao cadastrar usuario. Por favor verifique o formulário e tente novamente", Toast.LENGTH_LONG).show();
                                }


                            }
                        });
            } else {
                mProgessdialog.hide();
                Toast.makeText(AlugueTelaCadUsuario.this, "" +
                        "Falha ao cadastrar usuario. Todos os campos do formulário devem ser preenchidos...", Toast.LENGTH_LONG).show();

            }
        } else {
            mProgessdialog.hide();
            Toast.makeText(AlugueTelaCadUsuario.this, "" +
                    "As senhas senhas devem ser iguais, favor digitar novamente...", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(AlugueTelaCadUsuario.this, AlugueTelaLogin.class));
        return true;
    }

}
