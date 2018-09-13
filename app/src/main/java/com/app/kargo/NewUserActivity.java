package com.app.kargo;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.ProviderQueryResult;

public class NewUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSubscribeButton;
    private boolean isEmailExist = false;
    private boolean isAccountCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = (EditText) findViewById(R.id.email_edittext);
        mPasswordField = (EditText) findViewById(R.id.password_edittext);
        mSubscribeButton = (Button) findViewById(R.id.subscribe_submit_button);

        mSubscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });
    }

    private void createAccount(String email, String password) {

        Log.d("TAG", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // Check if email exist
        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if(task.isSuccessful()){
                    Log.d("TAG", "checkEmail:success");
                    if (task.getResult().getProviders().size() > 0) {
                        Toast.makeText(NewUserActivity.this, getApplication().getResources().getString(R.string.error_email_exist),
                                Toast.LENGTH_SHORT).show();
                        isEmailExist = true;
                    }
                } else {
                    Log.d("TAG", "checkEmail:fail");
                }
            }
        });

        if (!isEmailExist) {
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                sendEmailVerification();
                                Intent data = new Intent();
                                setResult(Activity.RESULT_OK, data);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(NewUserActivity.this, getApplication().getResources().getString(R.string.error_authentification),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // [END create_user_with_email]
        }
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            if (task.isSuccessful()) {
                                Toast.makeText(NewUserActivity.this,
                                        getApplication().getResources().getString(R.string.send_email_certification_ok) + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("TAG", "sendEmailVerification", task.getException());
                                Toast.makeText(NewUserActivity.this,
                                        getApplication().getResources().getString(R.string.send_email_certification_fail),
                                        Toast.LENGTH_SHORT).show();
                            }
                            // [END_EXCLUDE]
                        }
                    });
            // [END send_email_verification]
        } else {
            Toast.makeText(NewUserActivity.this,
                    getApplication().getResources().getString(R.string.send_email_certification_fail),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getApplication().getResources().getString(R.string.edittext_email_required));
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError(getApplication().getResources().getString(R.string.edittext_password_required));
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

}
