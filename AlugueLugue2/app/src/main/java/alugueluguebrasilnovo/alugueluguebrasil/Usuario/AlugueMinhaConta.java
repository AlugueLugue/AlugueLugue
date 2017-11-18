package alugueluguebrasilnovo.alugueluguebrasil.Usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import alugueluguebrasilnovo.alugueluguebrasil.Main.AlugueMain;
import alugueluguebrasilnovo.alugueluguebrasil.Main.DatabaseUtil;
import alugueluguebrasilnovo.alugueluguebrasil.Main.MainActivity;
import alugueluguebrasilnovo.alugueluguebrasil.R;
import id.zelory.compressor.Compressor;

public class AlugueMinhaConta extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private DatabaseReference mFotoUsuario;
    private DatabaseReference mEnviarFoto;
    private FirebaseUser mCurrenteUser;
    private StorageReference mStorageRef;

    private ProgressDialog dialog;
    private TextView nomeUsuario, email, telefone;
    private ImageButton imgContaEnviarFoto;
    private Button editarUser, Anuncios, btnimagem;
    private Uri imagemUri = null;

    private static final int GALLERY_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_minha_conta);
        ActionBar act = getSupportActionBar();
        act.setTitle("Meus Dados");
        DatabaseUtil.getDatabase();

        mCurrenteUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_id = mCurrenteUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mFotoUsuario = FirebaseDatabase.getInstance().getReference().child("usuario");
        mEnviarFoto = FirebaseDatabase.getInstance().getReference().child(current_id);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("usuario").child(current_id);
        nomeUsuario = (TextView) findViewById(R.id.txtnomeUser);
        email = (TextView) findViewById(R.id.txtemailUser);
        telefone = (TextView) findViewById(R.id.txttelUser);
        editarUser = (Button) findViewById(R.id.btnEdite);
        Anuncios = (Button) findViewById(R.id.btnAnuncios);
        btnimagem = (Button) findViewById(R.id.btnCapture);
        imgContaEnviarFoto = (ImageButton) findViewById(R.id.imgContaEnviarFoto);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("nome").getValue().toString();
                String tel = dataSnapshot.child("telefone").getValue().toString();
                String imguser = dataSnapshot.child("imagem").getValue().toString();

                nomeUsuario.setText(name);
                telefone.setText(tel);
                email.setText(mCurrenteUser.getEmail());
                Picasso.with(AlugueMinhaConta.this).load(imguser).into(imgContaEnviarFoto);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        editarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NomeEnviar = nomeUsuario.getText().toString();
                String TelefoneEnviar = telefone.getText().toString();

                Intent editarUser = new Intent(AlugueMinhaConta.this, AlugueEditarCadastro.class);
                editarUser.putExtra("nome", NomeEnviar);
                editarUser.putExtra("telefone", TelefoneEnviar);
                startActivity(editarUser);
            }
        });

        btnimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent galeriaIntent = new Intent();
                    galeriaIntent.setType("image/*");
                    galeriaIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galeriaIntent, GALLERY_PICK);


          }
        });
        Anuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anuncio = new Intent(AlugueMinhaConta.this, AlugueMain.class);
                startActivity(anuncio);
            }
        });

        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            imagemUri = data.getData();
            imgContaEnviarFoto.setImageURI(imagemUri);
            if (resultCode == RESULT_OK) {

                dialog = new ProgressDialog(AlugueMinhaConta.this);
                dialog.setTitle("Carregando imagem...");
                dialog.setMessage("Aguarde enquanto carregamos e processamos a imagem");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                String current_user_id = mCurrenteUser.getUid();

              StorageReference filepath = mStorageRef.child("Usuario_images").child(current_user_id + ".jpg");
                filepath.putFile(imagemUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            @SuppressWarnings("VisibleForTests")   final String download_url = task.getResult().getDownloadUrl().toString();



                            Map update_hashMap = new HashMap();
                            update_hashMap.put("imagem", download_url);


                            mUserDatabase.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        dialog.dismiss();
                                        Toast.makeText(AlugueMinhaConta.this, "Imagem enviada com sucesso", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(AlugueMinhaConta.this, "Você não carregou nenhuma imagem", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });


                        } else {

                            Toast.makeText(AlugueMinhaConta.this, "Erro ao carregar imagem", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }


                    }
                });

            } else {

                Toast.makeText(AlugueMinhaConta.this, "Erro no carregamento da imagem.", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(AlugueMinhaConta.this, MainActivity.class));
        return true;
    }
}