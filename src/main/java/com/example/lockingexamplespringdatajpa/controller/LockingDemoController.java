package com.example.lockingexamplespringdatajpa.controller;

import com.example.lockingexamplespringdatajpa.entity.BusDetails;
import com.example.lockingexamplespringdatajpa.repository.BusRepository;
import com.example.lockingexamplespringdatajpa.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LockingDemoController {

    private final BookingService bookingService;
    private final BusRepository busRepository;

    @GetMapping("/bookTicket")
    public void bookTicket() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(runInSeparateThread(bookingService::bookTicket));
        executorService.execute(runInSeparateThread(bookingService::bookAnotherTicket));
        executorService.shutdown();
    }

    @PostMapping("/addBus")
    public void addBus(@RequestParam String number, @RequestParam int capacity) {
        BusDetails busDetails = new BusDetails();
        busDetails.setCapacity(capacity);
        busDetails.setNumber(number);
        busDetails.setLocalDateTime(LocalDateTime.now());
        busRepository.save(busDetails);
    }


    private Runnable runInSeparateThread(FailableRunnable<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
