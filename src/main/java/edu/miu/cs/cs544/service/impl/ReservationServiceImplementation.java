package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImplementation implements ReservationService {

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        //        Write logic
        return null;
    }

    @Override
    public ReservationDTO getReservation(int id) throws CustomError {
        //        Write logic
        return null;
    }

    @Override
    public ReservationDTO updateReservation(int id, ReservationDTO reservationDTO) throws CustomError {
        //        Write logic
        return null;
    }

    @Override
    public void deleteReservation(int id) {
//        Write logic
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        //        Write Logic
        return null;
    }

    @Override
    public List<ReservationDTO> getAllReservationByProductType(ProductType productType) {
        //        Write Logic
        return null;
    }

    @Override
    public List<ReservationDTO> getAllReservationByReservationType(ReservationType reservationType) {
//        Write Logic
        return null;
    }
}
