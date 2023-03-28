package ru.clevertec.concurrency.client.impl;

import ru.clevertec.concurrency.data.Request;
import ru.clevertec.concurrency.client.Client;
import ru.clevertec.concurrency.exception.ClientException;
import ru.clevertec.concurrency.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Babrovich Siarhey on 24.03.2023
 */
public class ClientImpl implements Client {
    private final List<Integer> indexes;
    private final AtomicInteger accumulator;
    private final ExecutorService service;
    private final Lock lock;
    private List<CompletableFuture<Void>> futures;

    public ClientImpl(int limit, int threadsCount) {
        this.service = Executors.newFixedThreadPool(threadsCount);
        this.accumulator = new AtomicInteger();
        this.lock = new ReentrantLock();
        this.indexes = new ArrayList<>(Stream.iterate(1, x -> x + 1)
                .limit(limit)
                .toList());
    }

    @Override
    public void sendRequests(final Server server) {
        final Random random = new Random();
        this.futures = IntStream.range(0, indexes.size())
                .mapToObj(x -> getNext(random))
                .parallel()
                .map(i -> Request.builder()
                        .index(i)
                        .build())
                .map(request -> CompletableFuture.supplyAsync(() -> server.process(request), service)
                        .thenAcceptAsync(r -> accumulator.addAndGet(r.size()), service))
                .toList();
    }

    @Override
    public int getSum() {
        this.futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new ClientException(e);
            }
        });
        return this.accumulator.get();
    }

    /**
     * Generates a random index from 0 to size of indexes and returns the removed element from indexes at index
     * @param random - random generator
     * @return removed element
     */
    private Integer getNext(Random random) {
        this.lock.lock();
        try {
            final int rnd = random.nextInt(indexes.size());
            return this.indexes.remove(rnd);
        }finally {
            this.lock.unlock();
        }
    }

    /**
     * Returns size of indexes
     * @return size
     */
    public int getSize() {
        return indexes.size();
    }
}
