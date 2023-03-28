package ru.clevertec.concurrency.server;

import ru.clevertec.concurrency.data.Request;
import ru.clevertec.concurrency.data.Response;

import java.util.List;

/**
 * @author Babrovich Siarhey on 25.03.2023
 */
public interface Server {

    /**
     * Process a request and return a response.
     * @param request client request
     * @return server response
     */
    Response process(Request request);

    /**
     * Returns dao as List
     * @return list of integers
     */
    List<Integer> getResult();
}
