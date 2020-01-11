package com.sachith.gpacalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sachith.gpacalculator.department.bme.DisplaySemesterBME;
import com.sachith.gpacalculator.department.ce.DisplaySemesterCivil;
import com.sachith.gpacalculator.department.cpe.DisplaySemesterChemical;
import com.sachith.gpacalculator.department.cse.DisplaySemesterCSE;
import com.sachith.gpacalculator.department.ee.DisplaySemesterTrical;
import com.sachith.gpacalculator.department.entc.DisplaySemesterENTC;
import com.sachith.gpacalculator.department.er.DisplaySemesterEM;
import com.sachith.gpacalculator.department.me.DisplaySemesterMech;
import com.sachith.gpacalculator.department.mse.DisplaySemesterMaterial;
import com.sachith.gpacalculator.department.tlm.DisplaySemesterTLM;
import com.sachith.gpacalculator.department.tm.DisplaySemesterTM;
import com.sachith.gpacalculator.impl.DisplayModuleActivityFreshers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardViewBME = findViewById(R.id.BME);
        cardViewBME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterBME.class);
                intent.putExtra("Department", "BME");
                startActivity(intent);
            }
        });

        CardView cardViewCE = findViewById(R.id.CE);
        cardViewCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterCivil.class);
                intent.putExtra("Department", "CE");
                startActivity(intent);
            }
        });

        CardView cardViewCPE = findViewById(R.id.CPE);
        cardViewCPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterChemical.class);
                intent.putExtra("Department", "CPE");
                startActivity(intent);
            }
        });

        CardView cardViewCSE = findViewById(R.id.CSE);
        cardViewCSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterCSE.class);
                intent.putExtra("Department", "CSE");
                startActivity(intent);
            }
        });

        CardView cardViewEE = findViewById(R.id.EE);
        cardViewEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterTrical.class);
                intent.putExtra("Department", "EE");
                startActivity(intent);
            }
        });

        CardView cardViewENTC = findViewById(R.id.ENTC);
        cardViewENTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterENTC.class);
                intent.putExtra("Department", "ENTC");
                startActivity(intent);
            }
        });

        CardView cardViewER = findViewById(R.id.ER);
        cardViewER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterEM.class);
                intent.putExtra("Department", "ER");
                startActivity(intent);
            }
        });

        CardView cardViewME = findViewById(R.id.ME);
        cardViewME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterMech.class);
                intent.putExtra("Department", "ME");
                startActivity(intent);
            }
        });

        CardView cardViewMSE = findViewById(R.id.MSE);
        cardViewMSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterMaterial.class);
                intent.putExtra("Department", "MSE");
                startActivity(intent);
            }
        });

        CardView cardViewTLM = findViewById(R.id.TLM);
        cardViewTLM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterTLM.class);
                intent.putExtra("Department", "TLM");
                startActivity(intent);
            }
        });

        CardView cardViewTM = findViewById(R.id.TM);
        cardViewTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySemesterTM.class);
                intent.putExtra("Department", "TM");
                startActivity(intent);
            }
        });
    }

    public void displayFirstSem(View view) {

        Intent intent = new Intent(this, DisplayModuleActivityFreshers.class);
        startActivity(intent);
    }

    public void displaySemestersENTC(View view) {

        Intent intent = new Intent(this, DisplaySemesterENTC.class);
        startActivity(intent);
    }

    public void displaySemestersBME(View view) {

        Intent intent = new Intent(this, DisplaySemesterBME.class);
        startActivity(intent);
    }

    public void displaySemestersCE(View view) {

        Intent intent = new Intent(this, DisplaySemesterCivil.class);
        startActivity(intent);
    }

    public void displaySemestersChem(View view) {

        Intent intent = new Intent(this, DisplaySemesterChemical.class);
        startActivity(intent);
    }

    public void displaySemestersCSE(View view) {

        Intent intent = new Intent(this, DisplaySemesterCSE.class);
        startActivity(intent);
    }

    public void displaySemestersEE(View view) {

        Intent intent = new Intent(this, DisplaySemesterTrical.class);
        startActivity(intent);
    }

    public void displaySemestersER(View view) {

        Intent intent = new Intent(this, DisplaySemesterEM.class);
        startActivity(intent);
    }

    public void displaySemestersMech(View view) {

        Intent intent = new Intent(this, DisplaySemesterMech.class);
        startActivity(intent);
    }

    public void displaySemestersMSE(View view) {

        Intent intent = new Intent(this, DisplaySemesterMaterial.class);
        startActivity(intent);
    }

    public void displaySemestersTLM(View view) {

        Intent intent = new Intent(this, DisplaySemesterTLM.class);
        startActivity(intent);
    }

    public void displaySemestersTM(View view) {

        Intent intent = new Intent(this, DisplaySemesterTM.class);
        startActivity(intent);
    }


}
