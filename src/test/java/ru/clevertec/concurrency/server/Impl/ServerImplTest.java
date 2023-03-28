package ru.clevertec.concurrency.server.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.data.Request;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Babrovich Siarhey on 25.03.2023
 */
class ServerImplTest {

    private ServerImpl server;

    @BeforeEach
    void setUp() {
        server = new ServerImpl();
    }

    @Test
    void checkProcess() {
        AtomicInteger integer = new AtomicInteger(0);
        Stream.generate(() -> Request.builder()
                        .index(integer.getAndIncrement())
                        .build())
                .limit(10)
                .forEach(server::process);

        List<Integer> result = server.getResult();
        List<Integer> expected = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

    @Test
    void checkProcessParallel() {
        AtomicInteger integer = new AtomicInteger(0);
        int limit = 100;
        Stream.generate(() -> Request.builder()
                        .index(integer.getAndIncrement())
                        .build())
                .limit(limit)
                .parallel()
                .forEach(server::process);

        List<Integer> result = server.getResult();
        List<Integer> expected = Stream.iterate(0, x -> x + 1)
                .limit(limit)
                .toList();

        assertEquals(expected.size(), result.size());
        assertTrue(expected.containsAll(result));
    }
}
