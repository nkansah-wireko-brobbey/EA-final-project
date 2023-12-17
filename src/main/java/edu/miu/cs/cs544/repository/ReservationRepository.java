package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

}
