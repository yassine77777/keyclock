package com.primatec.accountservice.util.implementation;

import com.primatec.accountservice.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class IdGeneratorImpl implements IdGenerator {

    private int counter = 0;
    private static final Lock lock = new ReentrantLock();

    @Override
    public String autoGenerate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String formattedDateTime = dateFormat.format(now);
        lock.lock();
        try {

            counter++;

            return formattedDateTime + String.format("%06d", counter);
        } finally {
            lock.unlock();
        }
    }
}
