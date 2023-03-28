package ru.clevertec.concurrency.client;

import ru.clevertec.concurrency.server.Server;

/**
 * @author Babrovich Siarhey on 24.03.2023
 */
public interface Client {
    /**
     * Make Requests and send them to the server
     * @param server server to send requests
     */
    void sendRequests(Server server);

    /**
     * Waiting all responses from the server and return result
     * @return sum of results
     */
    int getSum();
}
