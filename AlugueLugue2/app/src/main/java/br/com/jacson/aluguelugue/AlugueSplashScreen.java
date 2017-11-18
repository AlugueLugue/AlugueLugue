package alugueluguebrasilnovo.alugueluguebrasil.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import alugueluguebrasilnovo.alugueluguebrasil.R;

/**
 * Created by NAILSON on 25/09/2017.
 */

public class AlugueSplashScreen extends Activity {

    private AlugueLugue alugueLugue;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseUtil.getDatabase();
        setContentView(R.layout.splashscreen);
        //FirebaseApp.initializeApp(AlugueSplashScreen.this);



        Thread tempoThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(4000);
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Anuncio");
                    Intent inicio = new Intent(AlugueSplashScreen.this, MainActivity.class);
                    startActivity(inicio);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        tempoThread.start();
    }


}
