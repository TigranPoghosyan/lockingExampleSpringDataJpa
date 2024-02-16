package com.example.lockingexamplespringdatajpa.repository;

import com.example.lockingexamplespringdatajpa.entity.BusDetails;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<BusDetails, Long> {

    //For optimistic locking
//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BusDetails> findWithLockingById(Long aLong);

}
