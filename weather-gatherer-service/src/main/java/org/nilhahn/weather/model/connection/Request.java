package org.nilhahn.weather.model.connection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private static Integer fieldLengthVersion = 2;
    private static Integer fieldLengthMethod = 8;
    private static Integer fieldLengthSeq = 2;
    private static Integer fieldLengthLength = 4;

    private String protocolVersion;
    private RequestMethod method;
    private Integer seq;
    private Integer length;
    private String data;

    public static Request fromString(String input) {
        Request request = new Request();
        Integer index = 0;

        request.setProtocolVersion(Request.readVersion(index, input));
        request.setMethod(Request.readMethod(index, input));
        request.setSeq(Request.readSequence(index, input));
        request.setLength(Request.readLength(index, input));
        request.setData(Request.readData(index, input));

        return request;
    }

    private static Integer readSequence(Integer index, String input) {
        return Integer.valueOf(Request.readField(index, input, Request.fieldLengthSeq));
    }

    private static Integer readLength(Integer index, String input) {
        return Integer.valueOf(Request.readField(index, input, Request.fieldLengthLength));
    }

    private static RequestMethod readMethod(Integer index, String input) {
        return RequestMethod.valueOf(Request.readField(index, input, Request.fieldLengthMethod));
    }

    private static String readVersion(Integer index, String input) {
        return Request.readField(index, input, Request.fieldLengthVersion);
    }


    private static String readData(Integer index, String input) {
        StringBuilder builder = new StringBuilder();

        for(;index < input.length(); index++) {
            builder.append(input.charAt(index));
        }

        return builder.toString().trim();
    }

    private static String readField(Integer index, String input, Integer fieldLength) {
        StringBuilder builder = new StringBuilder();

        if(input.length() < input.length() + fieldLength) {
            // TODO: throw Exception
        }

        for(int counter = 0; counter < fieldLength; counter++) {
            builder.append(input.charAt(++index));
        }
        return builder.toString().trim();
    }
}
