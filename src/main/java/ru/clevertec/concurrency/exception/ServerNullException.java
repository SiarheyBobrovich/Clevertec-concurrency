package ru.clevertec.concurrency.exception;

/**
 * @author Babrovich Siarhey on 27.03.2023
 */
public class ServerNullException extends NullPointerException {
    public ServerNullException(String message) {
        super(message);
    }
}
