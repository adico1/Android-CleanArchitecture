package com.tigaomobile.lockinapp.device;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tigaomobile.lockinapp.lockscreen.data.R;

/**
 * Created by adi on 14/03/2018.
 * Description:
 * Real Time Data Access Object
 */

public class RTDAO extends Service {
    private final String TAG = RTDAO.class.getSimpleName();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private static String lockValue = "";

    private void insert(String path, String value) {
        // Write a message to the database
        database.getReference().child(path).setValue(value);
    }

    public void update(String value) {
        // Write a message to the database

        myRef.setValue(value);
    }

    public String readLock() {
        return lockValue;
    }

    public void signup(String path) {
        // on signup the extenral app in not lock therefor the empty string in the url location
        insert(path, "");
        // init a realtime reference to the user path for real time updates from firebase
        init(path);
    }

    public void init(String path) {
        myRef = database.getReference(path);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.d(TAG, "Value is: " + value);
                lockValue = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public RTDAO getService() {
            return RTDAO.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        // Log.i(TAG, "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Log.i(TAG, "onDestroy");

        // TODO: disconnect from the DB.
        myRef = null;
        database = null;
        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();


}
