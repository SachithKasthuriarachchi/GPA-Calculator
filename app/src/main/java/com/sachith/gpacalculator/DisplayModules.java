package com.sachith.gpacalculator;

import com.sachith.gpacalculator.model.Module;

import java.util.ArrayList;

public interface DisplayModules {

    ArrayList<Module> getAllModules(String dbName, String tableName);
}
