package ru.clevertec.concurrency.exception;

/**
 * @author Babrovich Siarhey on 26.03.2023
 */
public class ClientException extends IllegalStateException {
    public ClientException(Exception e) {
        super(e);
    }
}
