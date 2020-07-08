package com.company;

public class Connection {
    private String connect = "\\W*((?i)connect(?-i))\\W*\\s\\w+[:]\\d+";
    private String disconnect = "\\W*((?i)disconnect(?-i))\\W*";

    private boolean isConnected(String input) {
        return input.matches(connect);
    }
    private boolean isDisconnected(String input) {
        return input.matches(disconnect);
    }

    public String checkConnection(String userInput){
        String tempInput = userInput;
        if(isConnected(tempInput)){
            return "connect";
        } else if(isDisconnected(tempInput)){
            return "disconnect";
        }

        return "";
    }


}
