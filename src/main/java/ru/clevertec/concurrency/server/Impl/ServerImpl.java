package ru.clevertec.concurrency.server.Impl;

import ru.clevertec.concurrency.data.Request;
import ru.clevertec.concurrency.data.Response;
import ru.clevertec.concurrency.exception.ServerNullException;
import ru.clevertec.concurrency.exception.ServerShouldDownException;
import ru.clevertec.concurrency.server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Babrovich Siarhey on 24.03.2023
 */
public class ServerImpl implements Server {

    private final Random random;
    private final List<Integer> dao;
    private final Lock lock;

    public ServerImpl() {
        this.random = new Random();
        this.dao = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public Response process(final Request request) {
        if (request == null) {
            throw new ServerNullException("Request must not be null");
        }
        final int index = request.getIndex();
        sleep();
        final int daoSize = add(index);
        return Response.builder()
                .size(daoSize)
                .build();
    }

    @Override
    public List<Integer> getResult() {
        return this.dao;
    }

    /**
     * Causes the thread to sleep between 100 and 1000 ms
     */
    private void sleep() {
        final int sleepTime = random.nextInt(100, 1001);
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new ServerShouldDownException(e);
        }
    }

    /**
     * Adds an index to the dao and returns the size of the dao
     * @param index added index
     * @return size
     */
    private int add(int index) {
        this.lock.lock();
        try {
            this.dao.add(index);
            return this.dao.size();
        }finally {
            this.lock.unlock();
        }
    }
}
