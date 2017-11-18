package alugueluguebrasilnovo.alugueluguebrasil.Main;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by NAILSON on 31/10/2017.
 */

public class DatabaseUtil {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }
}
