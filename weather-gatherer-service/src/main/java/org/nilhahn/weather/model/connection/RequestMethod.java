package org.nilhahn.weather.model.connection;

public enum RequestMethod {
    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private String method;

    RequestMethod(String methodStr) {
        method = methodStr;
    }

    public String getMethodString() {
        char[] result = new char[8];
        for(int index = 0; index < 8; index++) {
            if(index < method.length()) {
                result[index] = method.charAt(index);
            } else {
                result[index] = ' ';
            }
        }
        return new String(result);
    }
}
