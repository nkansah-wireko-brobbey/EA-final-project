package edu.miu.cs.cs544.repository;

import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Collection<Object> findAllByReservationType(ReservationType reservationType);

    Collection<Object> findAllByItems_Product_ProductType(ProductType productType);
}
