package edu.miu.cs.cs544.domain.adapter;

import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;

public class ReservationAdapter {

    public static ReservationDTO getReservationDTO(Reservation reservation){
        return new ReservationDTO(reservation.getId(),
//                reservation.getCustomer(),
                reservation.getItems(),
                reservation.getAuditData(),
                reservation.getReservationType());
    }

    public static Reservation getReservation(ReservationDTO reservationDTO){
        return new Reservation(reservationDTO.getId(),
                null,
                reservationDTO.getItems(),
                reservationDTO.getAuditData(),
                reservationDTO.getReservationType());
    }
}
