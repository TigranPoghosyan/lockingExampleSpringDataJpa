package com.example.lockingexamplespringdatajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bus_details")
public class BusDetails {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime localDateTime;

    private Integer capacity;

    private String number;

    //For optimistic locking
    @Version
    private Long version;

    @OneToMany(mappedBy = "busDetails")
    private Set<Ticket> tickets;

    public void addTicket(Ticket ticket) {
        ticket.setBusDetails(this);
        getTickets().add(ticket);
    }

}
