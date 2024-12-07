package ru.itis.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyCheckedException extends Exception {

    private final String myMessage;

    public MyCheckedException(String message, String myMessage) {
        super(message);
        this.myMessage = myMessage;
    }
}
