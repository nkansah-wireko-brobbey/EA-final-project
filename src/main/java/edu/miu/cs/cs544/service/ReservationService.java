package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;

public interface ReservationService {

    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationDTO getReservation(int id) throws CustomError;

}
