package com.company;

import java.util.HashMap;

public class Functions {
    private HashMap<String, Boolean> functions = new HashMap<String, Boolean>();
    private HashMap<String, Integer> testfunctions = new HashMap<String, Integer>();
    private HashMap<String, Integer> failMethod = new HashMap<String, Integer>();

    public void addFunc(String funcName) throws Exception {
        if (functions.containsKey(funcName)) {
            throw new Exception("This function is already declared!!!");
        } else {
            this.functions.put(funcName, true);
        }
    }

    public void addTestFunc(String funcName) throws Exception {
        if (failMethod.containsKey(funcName)) {
            throw new Exception("This function is already declared!!!");
        } else {
            this.failMethod.put(funcName, 0);
            this.testfunctions.put(funcName, 0);
        }
    }

    public String mostFailing() throws Exception {
        HashMap.Entry<String, Integer> maxEntry = null;
        for (HashMap.Entry<String, Integer> entry : failMethod.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        String name = maxEntry.getKey();
        int max = maxEntry.getValue();
        int calls = getTestCalls(name);

        return "Test " + name + " is run " + calls + " times and failed " + max + " times";
    }

    public String mostExecuted() throws Exception {
        HashMap.Entry<String, Integer> maxEntry = null;
        for (HashMap.Entry<String, Integer> entry : testfunctions.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        String name = maxEntry.getKey();
        int max = maxEntry.getValue();

        return "Test " + name + " is run " + max + " times";
    }

    public void checkExsistingTestMethod(String funcName) throws Exception {
        if (!testfunctions.containsKey(funcName)) {
            throw new Exception("There isn't such method!");
        } else {
            int calls = this.testfunctions.get(funcName);
            testfunctions.replace(funcName, calls+1);
        }
    }

    public void checkExsistingFailedTestMethod(String funcName) throws Exception {
        if (!failMethod.containsKey(funcName)) {
            throw new Exception("There isn't such method!");
        } else {
            int calls = this.failMethod.get(funcName);
            failMethod.replace(funcName, calls+1);
        }
    }

    public int getTestCalls(String funcName) throws Exception {
        if (!testfunctions.containsKey(funcName)) {
            throw new Exception("There isn't such method!");
        }
        return testfunctions.get(funcName);
    }
}
