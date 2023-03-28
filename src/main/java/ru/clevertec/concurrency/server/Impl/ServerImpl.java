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
    private final Lock lock = new ReentrantLock();
    public ServerImpl() {
        random = new Random();
        dao = new ArrayList<>();
    }

    @Override
    public Response process(Request request) {
        if (request == null) {
            throw new ServerNullException("Request must not be null");
        }
        int index = request.getIndex();
        sleep();
        int daoSize = add(index);
        return Response.builder()
                .size(daoSize)
                .build();
    }

    @Override
    public List<Integer> getResult() {
        return dao;
    }

    private void sleep() {
        int sleepTime = random.nextInt(100, 1001);
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new ServerShouldDownException(e);
        }
    }

    private int add(int index) {
        lock.lock();
        try {
            dao.add(index);
            return dao.size();
        }finally {
            lock.unlock();
        }
    }
}
