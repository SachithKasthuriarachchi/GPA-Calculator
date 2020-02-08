package com.sachith.gpacalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.editName);
        final CardView login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String index = name.getText().toString();
                if (!index.trim().equals("")) {
                    Intent intent = new Intent(v.getContext(), DisplayDeptActivity.class);
                    intent.putExtra("Index", index.toUpperCase());
                    startActivity(intent);
                }
            }
        });
    }
}
