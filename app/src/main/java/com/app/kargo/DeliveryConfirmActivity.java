package com.app.kargo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DeliveryConfirmActivity extends AppCompatActivity implements View.OnClickListener {
    private Button confirmButton;
    private Button eraseButton;

    int holder;

    private DrawingView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_confirm);

        Intent i = getIntent();
        holder = i.getIntExtra("holder", 1 );

        drawView = (DrawingView)findViewById(R.id.drawing);

        //set initial size
        drawView.setBrushSize(5);

        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);

        eraseButton = (Button) findViewById(R.id.erase_button);
        eraseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.confirm_button){

            Intent intent = new Intent();
            intent.putExtra("holder", holder);
            setResult(1, intent);
            finish();

        } else if (view.getId() == R.id.erase_button) {

            drawView.startNew();

        }

    }
}
