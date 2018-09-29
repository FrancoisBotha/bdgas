package io.francoisbotha.bdgasadmin.error;

import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class SjsException extends Exception{

    public SjsException(String Message) {
        super(Message);
    }
}
