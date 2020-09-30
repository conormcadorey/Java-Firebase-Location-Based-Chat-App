package com.example.conormcadorey.chatbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //Create instances for registration

    private TextInputLayout mFirstName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateAccount;

    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    //Progress Dialog

    private ProgressDialog mRegProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar set
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);


        //Firebase auth

        mAuth = FirebaseAuth.getInstance();

       //Registration fields

        mFirstName = (TextInputLayout) findViewById(R.id.reg_displayname);
        mEmail = (TextInputLayout) findViewById(R.id.reg_email);
        mPassword = (TextInputLayout) findViewById(R.id.reg_password);
        mCreateAccount = (Button) findViewById(R.id.reg_createaccount_btn);

        //Get users inputted details for registration

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = mFirstName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                //Ensure there are no blank fields submitted during registration

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))   {

                    //Show the profile registatration progress bar

                    mRegProgress.setTitle("Hold on a sec...");
                    mRegProgress.setMessage("Almost there chatter-box...");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(firstName, email, password);

                }

            }
        });
    }

    //When fields are complete, register new user to database

    private void register_user(final String firstName, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())    {

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    //Create a hashmap to add user registration data to the database

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", firstName);
                    userMap.put("status", "Add a custom status here");
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            //If user added to database - show success message

                            if (task.isSuccessful())   {

                                mRegProgress.dismiss();

                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Sends user back to phone home screen instead of application home screen
                                startActivity(mainIntent);
                                finish();

                            }
                        }
                    });

                //Error management - if there is a problem display the error message

                } else {

                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this, "Registration Error!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
