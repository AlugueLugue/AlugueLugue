package alugueluguebrasilnovo.alugueluguebrasil.Usuario;

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

import alugueluguebrasilnovo.alugueluguebrasil.R;

public class AlugueResetSenha extends AppCompatActivity {

    private EditText emailrecuperar;
    private Button enviar_recuperacao;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_reset_senha);

        ActionBar act = getSupportActionBar();
        act.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        emailrecuperar = (EditText) findViewById(R.id.emailUserSend);
        enviar_recuperacao = (Button) findViewById(R.id.btnEnviar);

    }


    public void startSendSenha(View view){

        mAuth.sendPasswordResetEmail(emailrecuperar.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(AlugueResetSenha.this, "" +
                                            "E-mail de recuperação enviado...",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AlugueResetSenha.this, AlugueTelaLogin.class));

                        }else {

                            Toast.makeText(AlugueResetSenha.this, "" +
                                            "Tente novamente...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(AlugueResetSenha.this, AlugueTelaLogin.class));
        return true;
    }
}
