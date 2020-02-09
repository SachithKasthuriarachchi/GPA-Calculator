package com.sachith.gpacalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.model.Module;

import java.util.ArrayList;

public class ModuleAdapter extends ArrayAdapter<Module> {

    private ArrayList<Module> selectedModules = new ArrayList<Module>();

    public ModuleAdapter(Context context, ArrayList<Module> modules) {
        super(context, 0, modules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Module module = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_checkbox, parent, false);
        }

        CheckBox modName = (CheckBox) convertView.findViewById(R.id.checkBoxMod);
        modName.setText(module.getName());
        modName.setTextColor(getContext().getResources().getColor(R.color.colorTextBright));

        modName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedModules.add(module);
                } else {
                    selectedModules.remove(module);
                }
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

    public ArrayList<Module> getSelectedModules() {
        return selectedModules;
    }
}
