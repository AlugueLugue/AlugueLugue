package alugueluguebrasilnovo.alugueluguebrasil.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.annotation.NonNull;

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

public class AlugueCadCnpj extends AppCompatActivity {

    private EditText edtrSocial, edtincEst, edtCnpj, edtEmail, edtSenha, edtCsenha, edtTel;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgessdialog;
    private DatabaseReference mDatabase;
    RadioGroup rdGroupCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_cad_cnpj);
        ActionBar act = getSupportActionBar();
        act.setDisplayHomeAsUpEnabled(true);
        act.setTitle("Pessoa Juridica");

        mProgessdialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        edtrSocial = (EditText) findViewById(R.id.rsPj);
        edtincEst = (EditText) findViewById(R.id.incrPj);
        edtCnpj = (EditText) findViewById(R.id.cnjUser);
        edtEmail = (EditText) findViewById(R.id.emailUserPj);
        edtSenha = (EditText) findViewById(R.id.senhaPj);
        edtTel = (EditText) findViewById(R.id.telUserPj);
        edtCsenha = (EditText) findViewById(R.id.senhaComfPj);


        rdGroupCad = (RadioGroup) findViewById(R.id.rGroupCad2);
        rdGroupCad.check(R.id.rbCnpj2);
        rdGroupCad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbCpf2) {
                    Intent intent = new Intent(AlugueCadCnpj.this, AlugueTelaCadUsuario.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    finish();
                }

            }
        });
    }

    public void cadastroPj(View v) {
        if (v.getId() == R.id.btnCadPj) {
            String nome = edtrSocial.getText().toString();
            String inscestadual = edtincEst.getText().toString();
            String cnpj = edtCnpj.getText().toString();
            String email = edtEmail.getText().toString();
            String senha = edtSenha.getText().toString();
            String telefone = edtTel.getText().toString();
            String tipo = "juridica";
            if (!TextUtils.isEmpty(nome) || !TextUtils.isEmpty(inscestadual)
                    || !TextUtils.isEmpty(cnpj) || !TextUtils.isEmpty(email) ||
                    !TextUtils.isEmpty(senha) || !TextUtils.isEmpty(telefone)) {

                mProgessdialog.setTitle("Salvando Usuario...");
                mProgessdialog.setMessage("Aguarde enquanto criamos sua conta!");
                mProgessdialog.setCanceledOnTouchOutside(false);
                mProgessdialog.show();
                register_user(nome, inscestadual, cnpj, email, senha, tipo, telefone);
            }
        }

        if (edtSenha.getText().length() < 6) {
            Toast.makeText(this, "Sua senha precisa conter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
        }


    }

    private void register_user(final String nome, final String inscestadual, final String cnpj, final String email, final String senha, final String tipo, final String telefone) {
        if (edtSenha.getText().toString().contentEquals(edtCsenha.getText().toString())) {
            if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(inscestadual)
                    && !TextUtils.isEmpty(cnpj) && !TextUtils.isEmpty(email)
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
                                    userMap.put("inscricao", inscestadual);
                                    userMap.put("cnpj", cnpj);
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
                                                Intent telaInicial = new Intent(AlugueCadCnpj.this, MainActivity.class);
                                                telaInicial.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(telaInicial);
                                                finish();
                                            }

                                        }
                                    });

                                } else {
                                    mProgessdialog.hide();
                                    Toast.makeText(AlugueCadCnpj.this, "" +
                                            "Falha ao cadastrar usuario. Por favor verifique o formulário e tente novamente", Toast.LENGTH_LONG).show();
                                }


                            }
                        });
            } else {
                mProgessdialog.hide();
                Toast.makeText(AlugueCadCnpj.this, "" +
                        "Falha ao cadastrar usuario. Todos os campos do formulário devem ser preenchidos...", Toast.LENGTH_LONG).show();

            }
        } else {
            mProgessdialog.hide();
            Toast.makeText(AlugueCadCnpj.this, "" +
                    "As senhas senhas devem ser iguais, favor digitar novamente...", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(AlugueCadCnpj.this, AlugueTelaLogin.class));
        return true;
    }

}
