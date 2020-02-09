package com.sachith.gpacalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.model.Module;
import com.sachith.gpacalculator.model.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This represents the results selection radio buttons in calculator_card.xml
 */

public class ResultsModAdapter extends ArrayAdapter<Module> {

    private ArrayList<Result> finalResults = new ArrayList<Result>();
    private static final Map<String, Double> resultSchema;

    static {
        Map<String, Double> schema = new HashMap<String, Double>();
        schema.put("A+", 4.2);
        schema.put("A", 4.0);
        schema.put("A-", 3.7);
        schema.put("B+", 3.3);
        schema.put("B", 3.0);
        schema.put("B-", 2.7);
        schema.put("C+", 2.3);
        schema.put("C", 2.0);
        schema.put("C-", 1.5);
        schema.put("D", 1.0);
        schema.put("I", 0.0);
        schema.put("F", 0.0);

        resultSchema = Collections.unmodifiableMap(schema);
    }


    public ResultsModAdapter(Context context, ArrayList<Module> enrolledModules) {
        super(context, 0, enrolledModules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Module module = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calculator_card, parent, false);
        }

        TextView moduleName = convertView.findViewById(R.id.calModuleName);
        moduleName.setText(module.getName());

        final TextView moduleCredits = convertView.findViewById(R.id.calCreditVal);
        moduleCredits.setText(String.valueOf(module.getCredits()));

        RadioGroup group = convertView.findViewById(R.id.radioGroup);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);
                RadioButton radio = (RadioButton) group.getChildAt(index);
                String myResult = radio.getText().toString();
                double gpa = resultSchema.get(myResult);
                double credits = Double.parseDouble(moduleCredits.getText().toString());
                Result result = new Result(credits, gpa, module.getName());
                finalResults.add(result);
            }
        });

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public ArrayList<Result> getFinalResults() {
        return finalResults;
    }

    public void clearFinalResults() {
        this.finalResults = new ArrayList<Result>();
    }

}
