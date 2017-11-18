package alugueluguebrasilnovo.alugueluguebrasil.Anuncio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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

import java.text.SimpleDateFormat;

import alugueluguebrasilnovo.alugueluguebrasil.Main.DatabaseUtil;
import alugueluguebrasilnovo.alugueluguebrasil.Main.MainActivity;
import alugueluguebrasilnovo.alugueluguebrasil.R;
import alugueluguebrasilnovo.alugueluguebrasil.buscaCep.Endereco;
import alugueluguebrasilnovo.alugueluguebrasil.buscaCep.Util;
import alugueluguebrasilnovo.alugueluguebrasil.buscaCep.ZipCodeListener;

public class AluguePublicarAnuncio extends AppCompatActivity {

    private TextView dataatual;
    private ImageButton add_imagem, add_imagem1, add_imagem2, add_imagem3, add_imagem4, add_imagem5;
    private EditText add_titulo, add_preco;
    private EditText add_desc, add_endereco, add_bairro, add_cidade;
    private Button publicar_anuncio;
    private Spinner add_uf;
    private Uri imagemUri, imagemUri1, imagemUri2, imagemUri3, imagemUri4, imagemUri5;


    private EditText cep;
    private Util util;
    private static final int GALLERY_REQUEST = 1;
    private static final int GALLERY_REQUEST1 = 2;
    private static final int GALLERY_REQUEST2 = 3;
    private static final int GALLERY_REQUEST3 = 4;
    private static final int GALLERY_REQUEST4 = 5;
    private static final int GALLERY_REQUEST5 = 6;
    private TextWatcher precoMask;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugue_publicar_anuncio);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Publicar Anúncio");
        DatabaseUtil.getDatabase();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String iduserAnuncio = mCurrentUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Anuncio");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child(iduserAnuncio);
        dataatual = (TextView) findViewById(R.id.dataatual);
        dataatual.setVisibility(View.INVISIBLE);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        String dateString = sdf.format(date);
        dataatual.setText(dateString);
        add_imagem = (ImageButton)findViewById(R.id.add_img);
        add_imagem1 = (ImageButton)findViewById(R.id.add_img1);
        add_imagem2 = (ImageButton)findViewById(R.id.add_img2);
        add_imagem3 = (ImageButton)findViewById(R.id.add_img3);
        add_imagem4 = (ImageButton)findViewById(R.id.add_img4);
        add_imagem5 = (ImageButton)findViewById(R.id.add_img5);
        add_titulo = (EditText)findViewById(R.id.edttitulo);
        add_desc = (EditText)findViewById(R.id.edtdesc);
        add_endereco = (EditText)findViewById(R.id.edtend);
        add_bairro = (EditText)findViewById(R.id.edtbairro);
        add_cidade = (EditText)findViewById(R.id.edtcidade);
        add_uf = (Spinner)findViewById(R.id.edtuf);
        add_preco = (EditText)findViewById(R.id.edtvalor);
        publicar_anuncio = (Button)findViewById(R.id.btn_enviar_anuncio);
        cep = (EditText)findViewById(R.id.edtcep);



        cep.addTextChangedListener(new ZipCodeListener(this));


        ArrayAdapter<CharSequence> adapteruf = ArrayAdapter
                .createFromResource(this,
                        R.array.estados,android.R.layout.simple_spinner_item);
        add_uf.setAdapter(adapteruf);

        util = new Util(this,
                R.id.edtcep,
                R.id.edtend,
                R.id.edtbairro,
                R.id.edtcidade,
                R.id.edtuf);

        dialog = new ProgressDialog(this);

        add_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent = new Intent();
                galeriaIntent.setAction(Intent.ACTION_GET_CONTENT);
                galeriaIntent.setType("image/*");
                startActivityForResult(galeriaIntent, GALLERY_REQUEST);
            }

        });

        add_imagem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent1 = new Intent();
                galeriaIntent1.setAction(Intent.ACTION_GET_CONTENT);
                galeriaIntent1.setType("image/*");
                startActivityForResult(galeriaIntent1, GALLERY_REQUEST1);
            }
        });

        add_imagem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent = new Intent();
                galeriaIntent.setAction(Intent.ACTION_GET_CONTENT);
                galeriaIntent.setType("image/*");
                startActivityForResult(galeriaIntent, GALLERY_REQUEST2);
            }

        });

        add_imagem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent1 = new Intent();
                galeriaIntent1.setAction(Intent.ACTION_GET_CONTENT);
                galeriaIntent1.setType("image/*");
                startActivityForResult(galeriaIntent1, GALLERY_REQUEST3);
            }
        });

        add_imagem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent = new Intent();
                galeriaIntent.setAction(Intent.ACTION_GET_CONTENT);
                galeriaIntent.setType("image/*");
                startActivityForResult(galeriaIntent, GALLERY_REQUEST4);
            }

        });

        add_imagem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent1 = new Intent();
                galeriaIntent1.setAction(Intent.ACTION_GET_CONTENT);
                galeriaIntent1.setType("image/*");
                startActivityForResult(galeriaIntent1, GALLERY_REQUEST5);
            }
        });

        publicar_anuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    enviarAnuncio();

                }
        });


    }


    private void enviarAnuncio() {

        dialog.setMessage("Enviando Anúncio...");

        final String titulo_valor = add_titulo.getText().toString().trim();
        final String desc_valor = add_desc.getText().toString().trim();
        final String cep_valor = cep.getText().toString().trim();
        final String end_valor = add_endereco.getText().toString().trim();
        final String bairro_valor = add_bairro.getText().toString().trim();
        final String cidade_valor = add_cidade.getText().toString().trim();
        final String preco_valor = add_preco.getText().toString().trim();
        final String uf_valor = add_uf.getSelectedItem().toString().trim();
        final String data = dataatual.getText().toString().trim();

        if(!TextUtils.isEmpty(titulo_valor) && !TextUtils.isEmpty(desc_valor) && imagemUri != null
                && imagemUri1 != null && imagemUri2 != null && imagemUri3 != null
                && imagemUri4 != null && imagemUri5 != null){

            dialog.show();
            StorageReference pastaImagem5 = mStorageRef.child("Imagem_Anuncio").child(imagemUri5.getLastPathSegment());
            pastaImagem5.putFile(imagemUri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings ("VisibleForTests") final Uri downloadUrl5 = taskSnapshot.getDownloadUrl();

                    StorageReference pastaImagem4 = mStorageRef.child("Imagem_Anuncio").child(imagemUri4.getLastPathSegment());
                    pastaImagem4.putFile(imagemUri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings ("VisibleForTests") final Uri downloadUrl4 = taskSnapshot.getDownloadUrl();


                            StorageReference pastaImagem3 = mStorageRef.child("Imagem_Anuncio").child(imagemUri3.getLastPathSegment());
                            pastaImagem3.putFile(imagemUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl3 = taskSnapshot.getDownloadUrl();


                                    StorageReference pastaImagem2 = mStorageRef.child("Imagem_Anuncio").child(imagemUri2.getLastPathSegment());
                                    pastaImagem2.putFile(imagemUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl2 = taskSnapshot.getDownloadUrl();


                                            StorageReference pastaImagem1 = mStorageRef.child("Imagem_Anuncio").child(imagemUri1.getLastPathSegment());
                                            pastaImagem1.putFile(imagemUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl1 = taskSnapshot.getDownloadUrl();


                                                    StorageReference pastaImagem = mStorageRef.child("Imagem_Anuncio").child(imagemUri.getLastPathSegment());


                                                    pastaImagem.putFile(imagemUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                           final DatabaseReference novoAnuncio = mDatabase.push();

                                                            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    novoAnuncio.child("titulo").setValue(titulo_valor);
                                                                    novoAnuncio.child("descricao").setValue(desc_valor);
                                                                    novoAnuncio.child("data").setValue(data);
                                                                    novoAnuncio.child("cep").setValue(cep_valor);
                                                                    novoAnuncio.child("endereço").setValue(end_valor);
                                                                    novoAnuncio.child("bairro").setValue(bairro_valor);
                                                                    novoAnuncio.child("cidade").setValue(cidade_valor);
                                                                    novoAnuncio.child("estado").setValue(uf_valor);
                                                                    novoAnuncio.child("preco").setValue(preco_valor);
                                                                    novoAnuncio.child("imagem").setValue(downloadUrl.toString());
                                                                    novoAnuncio.child("imagem1").setValue(downloadUrl1.toString());
                                                                    novoAnuncio.child("imagem2").setValue(downloadUrl2.toString());
                                                                    novoAnuncio.child("imagem3").setValue(downloadUrl3.toString());
                                                                    novoAnuncio.child("imagem4").setValue(downloadUrl4.toString());
                                                                    novoAnuncio.child("imagem5").setValue(downloadUrl5.toString());
                                                                    novoAnuncio.child("idusuario").setValue(mCurrentUser.getUid());

                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });


                                                            dialog.dismiss();

                                                            startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
                                                            finish();


                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });



                        }
                    });
                }
            });

        }if(!TextUtils.isEmpty(titulo_valor) && !TextUtils.isEmpty(desc_valor) && imagemUri != null
                && imagemUri1 != null && imagemUri2 != null && imagemUri3 != null
                && imagemUri4 != null && imagemUri5 == null){

            dialog.show();

                    StorageReference pastaImagem4 = mStorageRef.child("Imagem_Anuncio").child(imagemUri4.getLastPathSegment());
                    pastaImagem4.putFile(imagemUri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings ("VisibleForTests") final Uri downloadUrl4 = taskSnapshot.getDownloadUrl();


                            StorageReference pastaImagem3 = mStorageRef.child("Imagem_Anuncio").child(imagemUri3.getLastPathSegment());
                            pastaImagem3.putFile(imagemUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl3 = taskSnapshot.getDownloadUrl();


                                    StorageReference pastaImagem2 = mStorageRef.child("Imagem_Anuncio").child(imagemUri2.getLastPathSegment());
                                    pastaImagem2.putFile(imagemUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl2 = taskSnapshot.getDownloadUrl();


                                            StorageReference pastaImagem1 = mStorageRef.child("Imagem_Anuncio").child(imagemUri1.getLastPathSegment());
                                            pastaImagem1.putFile(imagemUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl1 = taskSnapshot.getDownloadUrl();


                                                    StorageReference pastaImagem = mStorageRef.child("Imagem_Anuncio").child(imagemUri.getLastPathSegment());


                                                    pastaImagem.putFile(imagemUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                            final DatabaseReference novoAnuncio = mDatabase.push();

                                                            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    novoAnuncio.child("titulo").setValue(titulo_valor);
                                                                    novoAnuncio.child("descricao").setValue(desc_valor);
                                                                    novoAnuncio.child("data").setValue(data);
                                                                    novoAnuncio.child("cep").setValue(cep_valor);
                                                                    novoAnuncio.child("endereço").setValue(end_valor);
                                                                    novoAnuncio.child("bairro").setValue(bairro_valor);
                                                                    novoAnuncio.child("cidade").setValue(cidade_valor);
                                                                    novoAnuncio.child("estado").setValue(uf_valor);
                                                                    novoAnuncio.child("preco").setValue(preco_valor);
                                                                    novoAnuncio.child("imagem").setValue(downloadUrl.toString());
                                                                    novoAnuncio.child("imagem1").setValue(downloadUrl1.toString());
                                                                    novoAnuncio.child("imagem2").setValue(downloadUrl2.toString());
                                                                    novoAnuncio.child("imagem3").setValue(downloadUrl3.toString());
                                                                    novoAnuncio.child("imagem4").setValue(downloadUrl4.toString());
                                                                    novoAnuncio.child("idusuario").setValue(mCurrentUser.getUid());

                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });


                                                            dialog.dismiss();

                                                            startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
                                                            finish();


                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });



                        }
                    });

        }
        if(!TextUtils.isEmpty(titulo_valor) && !TextUtils.isEmpty(desc_valor) && imagemUri != null
                && imagemUri1 != null && imagemUri2 != null && imagemUri3 != null
                && imagemUri4 == null && imagemUri5 == null){

            dialog.show();


                    StorageReference pastaImagem3 = mStorageRef.child("Imagem_Anuncio").child(imagemUri3.getLastPathSegment());
                    pastaImagem3.putFile(imagemUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl3 = taskSnapshot.getDownloadUrl();


                            StorageReference pastaImagem2 = mStorageRef.child("Imagem_Anuncio").child(imagemUri2.getLastPathSegment());
                            pastaImagem2.putFile(imagemUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl2 = taskSnapshot.getDownloadUrl();


                                    StorageReference pastaImagem1 = mStorageRef.child("Imagem_Anuncio").child(imagemUri1.getLastPathSegment());
                                    pastaImagem1.putFile(imagemUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl1 = taskSnapshot.getDownloadUrl();


                                            StorageReference pastaImagem = mStorageRef.child("Imagem_Anuncio").child(imagemUri.getLastPathSegment());


                                            pastaImagem.putFile(imagemUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                                    final DatabaseReference novoAnuncio = mDatabase.push();

                                                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            novoAnuncio.child("titulo").setValue(titulo_valor);
                                                            novoAnuncio.child("descricao").setValue(desc_valor);
                                                            novoAnuncio.child("data").setValue(data);
                                                            novoAnuncio.child("cep").setValue(cep_valor);
                                                            novoAnuncio.child("endereço").setValue(end_valor);
                                                            novoAnuncio.child("bairro").setValue(bairro_valor);
                                                            novoAnuncio.child("cidade").setValue(cidade_valor);
                                                            novoAnuncio.child("estado").setValue(uf_valor);
                                                            novoAnuncio.child("preco").setValue(preco_valor);
                                                            novoAnuncio.child("imagem").setValue(downloadUrl.toString());
                                                            novoAnuncio.child("imagem1").setValue(downloadUrl1.toString());
                                                            novoAnuncio.child("imagem2").setValue(downloadUrl2.toString());
                                                            novoAnuncio.child("imagem3").setValue(downloadUrl3.toString());
                                                            novoAnuncio.child("idusuario").setValue(mCurrentUser.getUid());

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });


                                                    dialog.dismiss();

                                                    startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
                                                    finish();


                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });



        }
        if(!TextUtils.isEmpty(titulo_valor) && !TextUtils.isEmpty(desc_valor) && imagemUri != null
                && imagemUri1 != null && imagemUri2 != null && imagemUri3 == null
                && imagemUri4 == null && imagemUri5 == null){

            dialog.show();



                    StorageReference pastaImagem2 = mStorageRef.child("Imagem_Anuncio").child(imagemUri2.getLastPathSegment());
                    pastaImagem2.putFile(imagemUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl2 = taskSnapshot.getDownloadUrl();


                            StorageReference pastaImagem1 = mStorageRef.child("Imagem_Anuncio").child(imagemUri1.getLastPathSegment());
                            pastaImagem1.putFile(imagemUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl1 = taskSnapshot.getDownloadUrl();


                                    StorageReference pastaImagem = mStorageRef.child("Imagem_Anuncio").child(imagemUri.getLastPathSegment());


                                    pastaImagem.putFile(imagemUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                            final DatabaseReference novoAnuncio = mDatabase.push();

                                            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    novoAnuncio.child("titulo").setValue(titulo_valor);
                                                    novoAnuncio.child("descricao").setValue(desc_valor);
                                                    novoAnuncio.child("data").setValue(data);
                                                    novoAnuncio.child("cep").setValue(cep_valor);
                                                    novoAnuncio.child("endereço").setValue(end_valor);
                                                    novoAnuncio.child("bairro").setValue(bairro_valor);
                                                    novoAnuncio.child("cidade").setValue(cidade_valor);
                                                    novoAnuncio.child("estado").setValue(uf_valor);
                                                    novoAnuncio.child("preco").setValue(preco_valor);
                                                    novoAnuncio.child("imagem").setValue(downloadUrl.toString());
                                                    novoAnuncio.child("imagem1").setValue(downloadUrl1.toString());
                                                    novoAnuncio.child("imagem2").setValue(downloadUrl2.toString());
                                                    novoAnuncio.child("idusuario").setValue(mCurrentUser.getUid());

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                            dialog.dismiss();

                                            startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
                                            finish();


                                        }
                                    });
                                }
                            });
                        }
                    });

        }
        if(!TextUtils.isEmpty(titulo_valor) && !TextUtils.isEmpty(desc_valor) && imagemUri != null
                && imagemUri1 != null && imagemUri2 == null && imagemUri3 == null
                && imagemUri4 == null && imagemUri5 == null){

            dialog.show();


                    StorageReference pastaImagem1 = mStorageRef.child("Imagem_Anuncio").child(imagemUri1.getLastPathSegment());
                    pastaImagem1.putFile(imagemUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl1 = taskSnapshot.getDownloadUrl();


                            StorageReference pastaImagem = mStorageRef.child("Imagem_Anuncio").child(imagemUri.getLastPathSegment());


                            pastaImagem.putFile(imagemUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                    final DatabaseReference novoAnuncio = mDatabase.push();

                                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            novoAnuncio.child("titulo").setValue(titulo_valor);
                                            novoAnuncio.child("descricao").setValue(desc_valor);
                                            novoAnuncio.child("data").setValue(data);
                                            novoAnuncio.child("cep").setValue(cep_valor);
                                            novoAnuncio.child("endereço").setValue(end_valor);
                                            novoAnuncio.child("bairro").setValue(bairro_valor);
                                            novoAnuncio.child("cidade").setValue(cidade_valor);
                                            novoAnuncio.child("estado").setValue(uf_valor);
                                            novoAnuncio.child("preco").setValue(preco_valor);
                                            novoAnuncio.child("imagem").setValue(downloadUrl.toString());
                                            novoAnuncio.child("imagem1").setValue(downloadUrl1.toString());
                                            novoAnuncio.child("idusuario").setValue(mCurrentUser.getUid());

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                    dialog.dismiss();

                                    startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
                                    finish();


                                }
                            });
                        }
                    });

        }
        if(!TextUtils.isEmpty(titulo_valor) && !TextUtils.isEmpty(desc_valor) && imagemUri != null
                && imagemUri1 == null && imagemUri2 == null && imagemUri3 == null
                && imagemUri4 == null && imagemUri5 == null){

            dialog.show();


                    StorageReference pastaImagem = mStorageRef.child("Imagem_Anuncio").child(imagemUri.getLastPathSegment());


                    pastaImagem.putFile(imagemUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            @SuppressWarnings("VisibleForTests") final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            final DatabaseReference novoAnuncio = mDatabase.push();

                            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    novoAnuncio.child("titulo").setValue(titulo_valor);
                                    novoAnuncio.child("descricao").setValue(desc_valor);
                                    novoAnuncio.child("data").setValue(data);
                                    novoAnuncio.child("cep").setValue(cep_valor);
                                    novoAnuncio.child("endereço").setValue(end_valor);
                                    novoAnuncio.child("bairro").setValue(bairro_valor);
                                    novoAnuncio.child("cidade").setValue(cidade_valor);
                                    novoAnuncio.child("estado").setValue(uf_valor);
                                    novoAnuncio.child("preco").setValue(preco_valor);
                                    novoAnuncio.child("imagem").setValue(downloadUrl.toString());
                                    novoAnuncio.child("idusuario").setValue(mCurrentUser.getUid());

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            dialog.dismiss();

                            startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
                            finish();


                        }
                    });

        }else{
            Toast.makeText(AluguePublicarAnuncio.this, "Você precisa inserir pelo menos uma imagem", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data );

    if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

        imagemUri = data.getData();
        add_imagem.setImageURI(imagemUri);

    }
    if (requestCode == GALLERY_REQUEST1 && resultCode == RESULT_OK) {
        imagemUri1 = data.getData();
        add_imagem1.setImageURI(imagemUri1);
    }

    if (requestCode == GALLERY_REQUEST2 && resultCode == RESULT_OK) {

        imagemUri2 = data.getData();
        add_imagem2.setImageURI(imagemUri2);

    }
    if (requestCode == GALLERY_REQUEST3 && resultCode == RESULT_OK) {
        imagemUri3 = data.getData();
        add_imagem3.setImageURI(imagemUri3);
    }

    if (requestCode == GALLERY_REQUEST4 && resultCode == RESULT_OK) {

        imagemUri4 = data.getData();
        add_imagem4.setImageURI(imagemUri4);

    }
    if (requestCode == GALLERY_REQUEST5 && resultCode == RESULT_OK) {
        imagemUri5 = data.getData();
        add_imagem5.setImageURI(imagemUri5);
    }
}



    @Override
    public void onBackPressed() {
        startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
        finish();

    }

    public String getUriRequest(){
        return "https://viacep.com.br/ws/"+cep.getText()+"/json/unicode/";
    }

    public void lockFields(boolean isToLock){
        util.lockFields( isToLock);
    }

    public void setDataViews(Endereco endereco){
        setFields(R.id.edtend, endereco.getLogradouro());
        setFields(R.id.edtbairro, endereco.getBairro());
        setFields(R.id.edtcidade, endereco.getLocalidade());
        setSpinner(R.id.edtuf, R.array.estados, endereco.getUf() );
    }

    private void setFields(int id, String data){
        ((EditText)findViewById(id)).setText(data);

    }
    private void setSpinner(int id, int arrayId, String data){
        String[] itens = getResources().getStringArray(arrayId);

        for(int i = 0; i < itens.length; i++){

            if (itens[i].endsWith("("+data+")")){
                ((Spinner)findViewById(id)).setSelection(i);
                return;
            }
        }
        ((Spinner)findViewById(id)).setSelection(0);
        Toast.makeText(AluguePublicarAnuncio.this, "CEP invalido", Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(AluguePublicarAnuncio.this, MainActivity.class));
        return true;
    }

}
