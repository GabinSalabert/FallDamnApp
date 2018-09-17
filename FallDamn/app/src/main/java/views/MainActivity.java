package views;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.example.gasalabert.falldown.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;

import model.Constants;
import views.MainThread;
import model.UserAccount;

import static android.content.ContentValues.TAG;

/**
 * Created by gasalabert on 31/01/18.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 123;
    private CheckBox checkBox = null;
    private CheckBox checkBox2 = null;
    public GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Constants.INTENT = intent;

        setContentView(R.layout.hohohome);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);

        Log.i("La bonne création", "onCreate");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //if(savedInstanceState != null)

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("La bonne pause", "onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("La bonne save", "onSaveInstanceState");
        //SharedPreferences

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Le bon restart", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("La bonne destruction", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("La bonne reprise", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        Log.i("Le bon start", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Le bon stop", "onStop");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            UserAccount.getInstance().setAccount(account);
            // Signed in successfully, show authenticated UI.
            updateUI(UserAccount.getInstance().getAccount());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void updateUI(GoogleSignInAccount account) {
        if (account!=null) {
            account.getIdToken();
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(account.getIdToken());
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    int value = dataSnapshot.getValue(int.class);
                    ((android.widget.TextView)findViewById(R.id.best_score)).setText(value);
                    Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }


    public void buttonClickFunction(View v){
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        intent.putExtra("mario", checkBox.isChecked());
        intent.putExtra("sonic", checkBox2.isChecked());
        startActivity(intent);
        MainThread.setKeepUpdate(true);
    }


}
