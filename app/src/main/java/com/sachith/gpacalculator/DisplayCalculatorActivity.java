package com.sachith.gpacalculator;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.sachith.gpacalculator.adapter.ResultsModAdapter;
import com.sachith.gpacalculator.model.Module;
import com.sachith.gpacalculator.model.Result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisplayCalculatorActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.calculator_view);

        Bundle selctions = getIntent().getExtras();
        if (selctions != null) {
            ArrayList<Module> selectedModules = selctions.getParcelableArrayList("Sel_Modules");

            if (selectedModules.size() > 0) {

                final ResultsModAdapter resultsModAdapter = new ResultsModAdapter(this, selectedModules);
                ListView listView = findViewById(R.id.listViewCal);
                listView.setAdapter(resultsModAdapter);

                ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                        ListView.LayoutParams.WRAP_CONTENT
                );

                Button button = new Button(this);
                button.setLayoutParams(params);
                button.setText(getResources().getString(R.string.calculate));
                button.setTextColor(getResources().getColor(R.color.colorText));
                button.setClickable(true);
                button.setFocusable(true);

                TypedArray selectableItemBG = this.obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    button.setForeground(selectableItemBG.getDrawable(0));
                }
                selectableItemBG.recycle();

                button.setBackgroundColor(getResources().getColor(R.color.colorSemList));
                listView.addFooterView(button);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Result> myResults = getUniqueResult(resultsModAdapter.getFinalResults());

                        if (myResults.size()>0) {

                            resultsModAdapter.clearFinalResults();
                            List<Double> results = getGPA(myResults);
                            Intent intent = new Intent(getApplicationContext(), ShowResult.class);
                            Bundle bundle = new Bundle();
                            bundle.putDouble("GPA", results.get(0));
                            bundle.putDouble("Credits", results.get(1));
                            intent.putExtras(bundle);
                            intent.putExtra("Index", getIntent().getStringExtra("Index"));
                            intent.putExtra("dept", getIntent().getStringExtra("dept"));
                            startActivity(intent);
                        }
                    }
                });
            }

        }

    }

    private List<Double> getGPA(ArrayList<Result> myResult) {

        double numerator = 0;
        double denominator = 0;
        List<Double> result = new ArrayList<Double>();

        for (Result r : myResult) {

            denominator += r.getCredits();
            numerator += (r.getCredits() * r.getResults());
        }
        result.add(numerator/denominator);
        result.add(denominator);

        return result;
    }

    private ArrayList<Result> getUniqueResult(ArrayList<Result> myResults) {

        ArrayList<Result> currentResult = new ArrayList<Result>();
        Set<String> modules = getModuleNames(myResults);
        for (String name : modules) {
            for (int i = myResults.size() - 1; i > -1; i--) {

                Result r = myResults.get(i);
                if (r.getName().equals(name)) {
                    currentResult.add(r);
                    break;
                }
            }

        }
        return currentResult;

    }

    private Set<String> getModuleNames(ArrayList<Result> myResults) {

        Set<String> modules = new HashSet<String>();
        for (Result r : myResults) {
            modules.add(r.getName());
        }
        return modules;
    }

}
