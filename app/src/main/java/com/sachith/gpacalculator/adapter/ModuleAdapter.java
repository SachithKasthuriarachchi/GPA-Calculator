package com.sachith.gpacalculator.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sachith.gpacalculator.R;
import com.sachith.gpacalculator.UserDBHelper;
import com.sachith.gpacalculator.UserReaderDB;
import com.sachith.gpacalculator.model.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleAdapter extends ArrayAdapter<Module> {

    private ArrayList<Module> selectedModules = new ArrayList<>();
    private String index;
    private String semester;
    private String department;

    public ModuleAdapter(Context context, ArrayList<Module> modules, String semester, String index, String department) {
        super(context, 0, modules);
        this.index = index;
        this.semester = semester;
        this.department = department;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Module module = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_checkbox, parent, false);
        }

        CheckBox modName = convertView.findViewById(R.id.checkBoxMod);
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

        modName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                UserDBHelper dbHelper = new UserDBHelper(getContext());
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                if (searchForQuery(index, Integer.parseInt(semester.substring(semester.length() - 1)),
                        module.getName(), dbHelper)) {

                    remove(module);
                    String selection = UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME + " = ? AND " +
                            UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " = ? AND " +
                            UserReaderDB.UserEntry.COLUMN_NAME_DEPARTMENT + " = ? AND " +
                            UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " =?";

                    String[] selectionArgs = {module.getName(), index, department, semester.substring(semester.length() - 1)};
                    database.delete(UserReaderDB.UserEntry.TABLE_NAME_MODULE, selection, selectionArgs);

                }
                return true;
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

    private boolean searchForQuery(String index, int semester, String moduleName, UserDBHelper dbHelper) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                UserReaderDB.UserEntry.COLUMN_NAME_CREDITS
        };

        String selection = UserReaderDB.UserEntry.COLUMN_NAME_SEMESTER + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_MODULE_NAME + " = ? AND " +
                UserReaderDB.UserEntry.COLUMN_NAME_INDEX + " =?";
        String[] selectionArgs = {String.valueOf(semester), moduleName, index};

        Cursor cursor = database.query(
                UserReaderDB.UserEntry.TABLE_NAME_MODULE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        List<Integer> moduleNameIDs = new ArrayList<>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserReaderDB.UserEntry.COLUMN_NAME_CREDITS));
            moduleNameIDs.add(itemId);
        }
        cursor.close();

        return moduleNameIDs.size() != 0;

    }
}
