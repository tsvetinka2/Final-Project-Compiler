package com.company;

import java.util.HashMap;

public class Variable {
    public HashMap<String, Integer> variables = new HashMap<String, Integer>();

    public HashMap<String, Integer> getVariableMap() {
        return variables;
    }

    public void add(String varName) throws Exception {
        if (variables.containsKey(varName)) {
            throw new Exception(varName + " is already declared!!!");
        } else {
            this.variables.put(varName, null);
        }
    }

    public void assignValue(String varName, int varValue) throws Exception {
        if (variables.containsKey(varName)) {
            this.variables.replace(varName, varValue);
        } else {
            throw new Exception(varName + " is not declared!!!");
        }
    }

    public void addTest(String varName, int varValue) throws Exception {
        if (variables.containsKey(varName)) {
            this.variables.replace(varName, varValue);
        } else {
            this.variables.put(varName, varValue);
        }
    }

    public void assignVariable(String varName1, String varName2) throws Exception {
        int assignValue = getValue(varName2);
        this.variables.replace(varName1, assignValue);
    }

    public int getValue(String varName) throws Exception {
        if (variables.containsKey(varName)) {
            Integer val = variables.get(varName);
            if (val == null) {
                throw new Exception((varName + " is not initialized!!!"));
            } else {
                return val;
            }
        } else {
            throw new Exception((varName + " is not declared!!!"));
        }
    }

}