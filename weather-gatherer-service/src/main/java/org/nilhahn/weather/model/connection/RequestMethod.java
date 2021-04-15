package org.nilhahn.weather.model.connection;

public enum RequestMethod {
    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    String method;

    RequestMethod(String methodStr) {
        method = methodStr;
    }

}
