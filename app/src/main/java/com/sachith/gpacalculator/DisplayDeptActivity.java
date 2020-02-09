package com.sachith.gpacalculator;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sachith.gpacalculator.impl.DisplayModuleActivityFreshers;

public class DisplayDeptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_view);

        CardView cardViewBME = findViewById(R.id.BME);
        cardViewBME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "BME");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewCE = findViewById(R.id.CE);
        cardViewCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "CE");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewCPE = findViewById(R.id.CPE);
        cardViewCPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "CPE");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewCSE = findViewById(R.id.CSE);
        cardViewCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "CSE");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewEE = findViewById(R.id.EE);
        cardViewEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "EE");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewENTC = findViewById(R.id.ENTC);
        cardViewENTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "ENTC");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewER = findViewById(R.id.ER);
        cardViewER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "ER");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewME = findViewById(R.id.ME);
        cardViewME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "ME");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewMSE = findViewById(R.id.MSE);
        cardViewMSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "MSE");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewTLM = findViewById(R.id.TLM);
        cardViewTLM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "TLM");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewTM = findViewById(R.id.TM);
        cardViewTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesters.class);
                intent.putExtra("Department", "TM");
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewFresher = findViewById(R.id.firstSem);
        cardViewFresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayModuleActivityFreshers.class);
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });

        CardView cardViewDashBoard = findViewById(R.id.dashClick);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TypedArray selectableItemBG = this.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
            cardViewDashBoard.setForeground(selectableItemBG.getDrawable(0));
        }
        cardViewDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DashBoardActivity.class);
                intent.putExtra("Index", getIntent().getStringExtra("Index"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void displayFirstSem(View view) {

        Intent intent = new Intent(this, DisplayModuleActivityFreshers.class);
        startActivity(intent);
    }

    public void displaySemesters(View view) {

        Intent intent = new Intent(this, DisplaySemesters.class);
        startActivity(intent);
    }
}
