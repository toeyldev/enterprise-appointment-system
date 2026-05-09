package edu.sjsu.cmpe172.starterdemo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SystemMetricsService {

    private final AtomicInteger successfulBookings = new AtomicInteger(0);
    private final AtomicInteger failedBookings = new AtomicInteger(0);
    private final AtomicLong totalBookingLatencyMs = new AtomicLong(0);
    private final AtomicInteger bookingAttempts = new AtomicInteger(0);

    // record successful booking metrics
    public void recordSuccessfulBooking(long latencyMs) {
        successfulBookings.incrementAndGet();
        bookingAttempts.incrementAndGet();
        totalBookingLatencyMs.addAndGet(latencyMs);
    }

    // record failed booking metrics
    public void recordFailedBooking(long latencyMs) {
        failedBookings.incrementAndGet();
        bookingAttempts.incrementAndGet();
        totalBookingLatencyMs.addAndGet(latencyMs);
    }

    public int getSuccessfulBookings() {
        return successfulBookings.get();
    }

    public int getFailedBookings() {
        return failedBookings.get();
    }

    // calculate average booking latency
    public double getAverageBookingLatencyMs() {
        int attempts = bookingAttempts.get();

        if (attempts == 0) {
            return 0;
        }

        return (double) totalBookingLatencyMs.get() / attempts;
    }
}