package com.app.kargo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    Button LoginButton;
    Button SignupButton;

    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity = this;

        LoginButton = (Button) findViewById(R.id.login_goto_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToConnection();
            }
        });

        SignupButton = (Button) findViewById(R.id.signup_goto_button);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSubscription();
            }
        });
    }

    private void goToConnection() {
        final Intent goToConnectionIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(goToConnectionIntent, 1);
    }

    private void goToSubscription() {
        final Intent goToSubscriptionIntent = new Intent(this, NewUserActivity.class);
        startActivityForResult(goToSubscriptionIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                final Intent MainActivity = new Intent(this, MainActivity.class);
                startActivity(MainActivity);
                finish();
            }
        } else if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                goToConnection();
            }
        }
    }

}
