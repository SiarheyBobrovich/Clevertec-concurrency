package ru.clevertec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.client.Client;
import ru.clevertec.concurrency.client.impl.ClientImpl;
import ru.clevertec.concurrency.server.Impl.ServerImpl;
import ru.clevertec.concurrency.server.Server;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTest {

    private Server server;
    private Client client;
    private final int limit = 1000;

    @BeforeEach
    void setUp() {
        server = new ServerImpl();
        client = new ClientImpl(limit, 4);
    }

    @Test
    void checkClientSum() {
        int expected = (limit + 1) * limit / 2;
        client.sendRequests(server);
        int sum = client.getSum();

        assertEquals(expected, sum);
    }

    @Test
    void checkServerResult() {
        client.sendRequests(server);
        client.getSum();
        List<Integer> serverResult = server.getResult();
        List<Integer> expected = Stream.iterate(1, x -> x + 1)
                .limit(limit)
                .toList();

        assertEquals(limit, serverResult.size());
        assertTrue(expected.containsAll(serverResult));
    }
}
