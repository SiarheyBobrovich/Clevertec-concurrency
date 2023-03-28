package ru.clevertec.concurrency.client.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.concurrency.data.Request;
import ru.clevertec.concurrency.data.Response;
import ru.clevertec.concurrency.server.Server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Babrovich Siarhey on 25.03.2023
 */
@ExtendWith(MockitoExtension.class)
class ClientImplTest {
    private ClientImpl client;
    @Mock
    private Server server;
    private final int limit = 1000;

    @BeforeEach
    void setUp() {
        client = new ClientImpl(limit, 4);
    }

    @AfterEach
    void clear() {
        reset(server);
    }
    @Test
    void checkGetSize() {
        Response response = Response.builder().size(0).build();
        doReturn(response).when(server).process(any(Request.class));
        client.sendRequests(server);
        int size = client.getSize();

        assertEquals(0, size);
    }

    @Test
    void checkSum() {
        Response response = Response.builder().size(1).build();
        doReturn(response).when(server).process(any(Request.class));

        client.sendRequests(server);
        int sum = client.getSum();

        assertEquals(limit, sum);
    }

    @Test
    void checkSendRequests() {
        Response response = Response.builder().size(1).build();
        doReturn(response).when(server).process(any(Request.class));

        client.sendRequests(server);
        client.getSum();

        verify(server, atLeast(limit)).process(any(Request.class));
    }
}
