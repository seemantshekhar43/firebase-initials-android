package comandydevo.wixsite.seemantshekhar43.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // introducing the variables
    EditText email;
    EditText password;

    // introducing firebase instance
    private FirebaseAuth mAuth;


    // checking whether the user is already signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!= null) {
            Toast.makeText(this, "Already In", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising firebase instance
        mAuth = FirebaseAuth.getInstance();

        // setting the variables
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);



    }

        public void onRegister(View view){

            //converting text input into String
            final String myEmail = email.getText().toString();
            final String myPassword = password.getText().toString();

            mAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("log1", "createUserWithEmail:success");
                                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("log2", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }

        //logIn method
    public void onLogIn(View view){
        final String myEmail = email.getText().toString();
        final String myPassword = password.getText().toString();
        mAuth.signInWithEmailAndPassword(myEmail, myPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                            Log.i("Tag", "signInWithEmail:success" +user.toString());

                            // lets get the user id i.e. UID
                            String userID = user.getUid().toString();
                            Log.i("UID","USER ID: " + userID);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("Tag", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    //logOut method
    public void onLogOut (View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this,"Signed Out Successfully",Toast.LENGTH_SHORT).show();
    }



}
