package ru.clevertec.concurrency.exception;

/**
 * @author Babrovich Siarhey on 25.03.2023
 */
public class ServerShouldDownException extends RuntimeException {
    public ServerShouldDownException(InterruptedException e) {
        super(e);
    }
}
