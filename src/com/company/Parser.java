package com.company;

import java.util.HashMap;

public class Parser {
    private String declarationRE = "\\W*((?i)int(?-i))\\W*\\s\\w+;";  //int a;
    private String fullDeclarationRE = "\\W*((?i)int(?-i))\\W*\\s\\w+\\s=\\s\\w+;";  //int a = 3; || int c = b;
    private String operationRE = "\\w+\\s[\\/\\+\\-\\*]\\s\\w+";  //a + b
    private String callVariableRE = "\\w+";  //a
    private String assignToVariableRE = "\\w+\\s=\\s\\d+;";  //a = 5;
    private String mathOperations = "\\w+\\s=\\s(\\w+((\\s[+]\\s)+|(\\s[-]\\s))+)+\\w+;";  //
    private String decMathOperations = "\\W*((?i)int(?-i))\\W*\\s\\w+\\s=\\s(\\w+((\\s[+]\\s)+|(\\s[-]\\s))+)+\\w+;";  // int d = a + b - c;
    private String decMethod = "(\\W*((?i)int(?-i))\\W*\\s\\w+)([(]\\s?(\\w+\\s\\w+[,]?\\s?)+[)])\\s[{]"; //int sum(int a, int b) {
    private String ret = "\\W*((?i)return(?-i))\\W*\\s\\w+;"; //return
    private String newLine = "" +
            "";
    private String endBracket = "\\W*((?i)}(?-i))\\W*"; //}
    private String func = "(\\w+\\s)([(]\\s\\w+\\s[,]\\s\\w+[)])"; //sum (a,b)
    private String assignFunction = "(\\w\\s[=]\\s\\w+\\s)([(]\\s\\w+\\s[,]\\s\\w+[)];)"; // d = sum ( a , c);
    private String testFunc = "(\\W*((?i)test(?-i))\\W*\\s\\w+[(][)]\\s[{])"; //test testMethod1() {
    private String assertFunc = "(\\W*((?i)assert(?-i))\\W*\\s\\w+[,]\\s\\w+\\s[(]\\d+[,]\\s\\d+[)];)";  //assert a, sum (5, 5);
    private String callTestMethod = "\\W*((?i)test(?-i))\\W*\\s\\w+[(][)];";  //test testMethod1();
    private String mostFailingTest = "\\W*((?i)most(?-i))\\W*[-]\\W*((?i)failing(?-i))\\W*[-]\\W*((?i)test(?-i))\\W*";
    private String mostExecutedTest = "\\W*((?i)most(?-i))\\W*[-]\\W*((?i)executed(?-i))\\W*[-]\\W*((?i)test(?-i))\\W*";

    private Variable variable = new Variable();
    private Functions functions = new Functions();


    private boolean isDeclaration(String input) {
        return input.matches(declarationRE);
    }

    private boolean isFullDeclaration(String input) {
        return input.matches(fullDeclarationRE);
    }

    private boolean isOperation(String input) {
        return input.matches(operationRE);
    }

    private boolean isVarCall(String input) {
        return input.matches(callVariableRE);
    }

    private boolean isAssignment(String input) {
        return input.matches(assignToVariableRE);
    }

    private boolean isMathOperation(String input) {
        return input.matches(mathOperations);
    }

    private boolean isDecMathOperation(String input) {
        return input.matches(decMathOperations);
    }

    private boolean isDecMethod(String input) {
        return input.matches(decMethod);
    }

    private boolean isReturn(String input) {
        return input.matches(ret);
    }

    private boolean isNewLine(String input) {
        return input.matches(newLine);
    }

    private boolean isEndBracket(String input) {
        return input.matches(endBracket);
    }

    private boolean isfunc(String input) {
        return input.matches(func);
    }

    private boolean isAssignFunction(String input) {
        return input.matches(assignFunction);
    }

    private boolean isCallTestMethod(String input) {
        return input.matches(callTestMethod);
    }

    private boolean isTestFunc(String input) {
        return input.matches(testFunc);
    }

    private boolean isAssertFunc(String input) {
        return input.matches(assertFunc);
    }

    private boolean isMostFailingTest(String input) {
        return input.matches(mostFailingTest);
    }

    private boolean isMostExecutedTest(String input) {
        return input.matches(mostExecutedTest);
    }

    public String parse(String input) throws Exception {
        String tempInput = input;
        String[] elements = tempInput.split("\\s+");

        if (isDeclaration(tempInput)) {
            String varName = elements[1].substring(0, elements[1].length() - 1);
            try {
                this.variable.add(varName);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isFullDeclaration(tempInput)) {
            String varName = elements[1];
            String val = elements[3].substring(0, elements[3].length() - 1);
            int varValue = 0;

            if (isNumber(val)) {
                varValue = Integer.parseInt(val);
                try {
                    this.variable.add(varName);
                    this.variable.assignValue(varName, varValue);
                } catch (Exception e) {
                    throw new Exception(e.toString());
                }
            } else {
                try {
                    this.variable.add(varName);
                    this.variable.assignVariable(varName, val);
                } catch (Exception e) {
                    throw new Exception(e.toString());
                }
            }
        } else if (isOperation(tempInput)) {
            String varName1 = elements[0];
            String option = elements[1];
            String varName2 = elements[2];
            try {
                if (option.equals("+")) {
                    int x = variable.getValue(varName1);
                    int y = variable.getValue(varName2);
                    int sum = 0;
                    sum = x + y;
                    return Integer.toString(sum);
                } else if (option.equals("-")) {
                    int x = variable.getValue(varName1);
                    int y = variable.getValue(varName2);
                    int sum = 0;
                    sum = x - y;
                    return Integer.toString(sum);
                }
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isAssignment(tempInput)) {
            String varName = elements[0];
            int varValue = Integer.parseInt(elements[2].substring(0, elements[2].length() - 1));
            try {
                this.variable.assignValue(varName, varValue);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isVarCall(tempInput)) {
            String varName = elements[0];
            try {
                return Integer.toString(this.variable.getValue(varName));
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isMathOperation(tempInput)) {
            String varName = elements[0];
            String lastElement = elements[elements.length - 1];
            elements[elements.length - 1] = lastElement.substring(0, lastElement.length() - 1);
            int result = 0;
            int countOperation = 0;
            for (int i = 2; i < elements.length - 1; i++) {
                if (!(i % 2 == 0)) {
                    if (elements[i].equals("+")) {
                        if (countOperation == 0) {
                            result = this.variable.getValue(elements[i - 1]) + this.variable.getValue(elements[i + 1]);
                        } else {
                            result += this.variable.getValue(elements[i + 1]);
                        }
                    } else {
                        if (countOperation == 0) {
                            result = this.variable.getValue(elements[i - 1]) - this.variable.getValue(elements[i + 1]);
                        } else {
                            result -= this.variable.getValue(elements[i + 1]);
                        }
                    }
                    countOperation++;
                }
            }
            variable.assignValue(varName, result);
        } else if (isDecMathOperation(tempInput)) {
            try {
                String varName = elements[1];
                String lastElement = elements[elements.length - 1];
                elements[elements.length - 1] = lastElement.substring(0, lastElement.length() - 1);
                int result = 0;
                int countOperation = 0;
                for (int i = 3; i < elements.length - 1; i++) {
                    if (i % 2 == 0) {
                        if (elements[i].equals("+")) {
                            if (countOperation == 0) {
                                result = this.variable.getValue(elements[i - 1]) + this.variable.getValue(elements[i + 1]);
                            } else {
                                result += this.variable.getValue(elements[i + 1]);
                            }
                        } else {
                            if (countOperation == 0) {
                                result = this.variable.getValue(elements[i - 1]) - this.variable.getValue(elements[i + 1]);
                            } else {
                                result -= this.variable.getValue(elements[i + 1]);
                            }
                        }
                        countOperation++;
                    }
                }
                this.variable.add(varName);
                variable.assignValue(varName, result);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isDecMethod(tempInput)) {
            try {
                String funcName = elements[1];
                this.functions.addFunc(funcName);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isReturn(tempInput)) {

        } else if (isNewLine(tempInput)) {

        } else if (isEndBracket(tempInput)) {

        } else if (isfunc(tempInput)) {
            String sumString = elements[0];
            String varName1 = elements[2];
            String varName2 = elements[elements.length - 1];
            varName2 = varName2.substring(0, varName2.length() - 1);
            int x = variable.getValue(varName1);
            int y = variable.getValue(varName2);
            int sum = 0;
            sum = x + y;
            this.variable.add(sumString);
            this.variable.assignValue(sumString, sum);
            return Integer.toString(this.variable.getValue(sumString));
        } else if (isAssignFunction(tempInput)) {
            try {
                String varName = elements[0];
                String varName1 = elements[4];
                String varName2 = elements[elements.length - 1];
                varName2 = varName2.substring(0, varName2.length() - 2);
                int x = variable.getValue(varName1);
                int y = variable.getValue(varName2);
                int sum = 0;
                sum = x + y;
                this.variable.assignValue(varName, sum);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isAssertFunc(tempInput)) {
            String firstValue = elements[1];
            firstValue = firstValue.substring(0, firstValue.length() - 1);
            int z = this.variable.getValue(firstValue);
            this.variable.assignValue(firstValue, z);

            String secondValue = elements[2]; //sum
            String varName1 = elements[3];  //5
            varName1 = varName1.substring(1, varName1.length() - 1);
            String varName2 = elements[elements.length - 1];
            varName2 = varName2.substring(0, varName2.length() - 2);//5
            int x = Integer.parseInt(varName1);
            int y = Integer.parseInt(varName2);
            int sum = 0;
            sum = x + y;
            this.variable.addTest(secondValue, sum);
        } else if (isCallTestMethod(tempInput)) {
            try {
                String funcName = elements[1];
                funcName = funcName.substring(0, funcName.length() - 1);
                this.functions.checkExsistingTestMethod(funcName);
                HashMap<String, Integer> var = variable.getVariableMap();
                int actual = var.get("a");
                int expected = var.get("sum");

                if (actual == expected) {
                    return funcName + " runs successfully";
                } else {
                    functions.checkExsistingFailedTestMethod(funcName);
                    return funcName + " fails";
                }
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        } else if (isTestFunc(tempInput)) {
            try {
                String funcName = elements[1];
                this.functions.addTestFunc(funcName);
            } catch (Exception e) {

                throw new Exception(e.toString());
            }
        } else if (isMostFailingTest(tempInput)) {

            return this.functions.mostFailing();

        }else if(isMostExecutedTest(tempInput)){
            return this.functions.mostExecuted();
        }
        else {

            throw new Exception("There is not such option.");
        }

        return "";
    }

    public boolean isNumber(String val) {
        try {
            Integer.parseInt(val);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
