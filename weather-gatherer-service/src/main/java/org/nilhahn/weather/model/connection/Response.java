package org.nilhahn.weather.model.connection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private String protocolVersion;
    private RequestMethod method;
    private Integer seq;
    private Integer length;
    private String data;

    @Override
    public String toString() {
        return protocolVersion + this.createFieldMethod(method) + this.createFieldSequence(seq) + this.createFieldLength(length) + data;
    }

    private String createFieldMethod(RequestMethod method) {
        return method.toString();
    }

    private String createFieldLength(Integer length) {
        return String.valueOf(length);
    }

    private String createFieldSequence(Integer seq) {
        // TODO: create field 
        return String.valueOf(seq);
    }
}
