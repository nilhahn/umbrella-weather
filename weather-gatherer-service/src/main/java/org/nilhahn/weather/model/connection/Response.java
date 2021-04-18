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
        return method.getMethodString();
    }

    private String createFieldLength(Integer length) {
        return String.valueOf(length);
    }

    private String createFieldSequence(Integer seq) {
        String sequenceString = String.valueOf(seq);
        char[] result = new char[2];

        for(int index = 0; index < 2; index++) {
            if(index < sequenceString.length()) {
                result[index] = sequenceString.charAt(index);
            } else {
                result[0] = ' ';
            }
        }

        return new String(result);
    }
}
