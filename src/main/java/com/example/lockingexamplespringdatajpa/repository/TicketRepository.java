package com.example.lockingexamplespringdatajpa.repository;

import com.example.lockingexamplespringdatajpa.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
