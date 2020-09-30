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

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mSigninEmail;
    private TextInputLayout mSigninPassword;
    private Button mSignin_btn;
    private ProgressDialog mLoginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //Toolbar set
        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign in");

        mLoginProgress = new ProgressDialog(this);

        mSigninEmail = (TextInputLayout) findViewById(R.id.login_email);
        mSigninPassword = (TextInputLayout) findViewById(R.id.login_password);
        mSignin_btn = (Button) findViewById(R.id.login_btn);

        mSignin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mSigninEmail.getEditText().getText().toString();
                String password = mSigninPassword.getEditText().getText().toString();

                //Check if login fields have been filled in correctly to initiate login process

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))   {

                    mLoginProgress.setTitle("Signing in...");
                    mLoginProgress.setMessage("We're almost in...");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(email, password);

                }

            }
        });


    }

    private void loginUser(String email, String password)   {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    mLoginProgress.dismiss();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Sends user back to phone home screen instead of application home screen
                    startActivity(mainIntent);
                    finish();

                } else {

                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Sign in Error - Try again!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
