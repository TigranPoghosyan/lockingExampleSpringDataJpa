package com.example.lockingexamplespringdatajpa.service;

import com.example.lockingexamplespringdatajpa.entity.BusDetails;
import com.example.lockingexamplespringdatajpa.entity.Ticket;
import com.example.lockingexamplespringdatajpa.exception.SeatNotAvailableException;
import com.example.lockingexamplespringdatajpa.repository.BusRepository;
import com.example.lockingexamplespringdatajpa.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BusRepository busRepository;

    private final TicketRepository ticketRepository;

    private void saveTicket(String firstName, String lastName,String gender, BusDetails busDetails) {
        if (busDetails.getCapacity() <= busDetails.getTickets().size()) {
            throw new SeatNotAvailableException();
        }
        Ticket ticket = createTicketAndAddInBusDetails(firstName, lastName, gender, busDetails);

        ticketRepository.save(ticket);
    }

    private static Ticket createTicketAndAddInBusDetails(String firstName, String lastName, String gender, BusDetails busDetails) {
        Ticket ticket = Ticket.builder()
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .build();

        busDetails.addTicket(ticket);
        return ticket;
    }

    @Transactional
    public void bookTicket() throws InterruptedException {
        Optional<BusDetails> busDetail = busRepository.findWithLockingById(1L);
        busDetail.ifPresent(busDetails -> saveTicket("John", "Allen", "Male", busDetails));
        Thread.sleep(1000);
    }

    @Transactional
    public void bookAnotherTicket() throws InterruptedException {
        Optional<BusDetails> busDetail = busRepository.findById(1L);
        busDetail.ifPresent(busDetails -> saveTicket("Maria", "Johnson", "Female", busDetails));
        Thread.sleep(1000);
    }

}
