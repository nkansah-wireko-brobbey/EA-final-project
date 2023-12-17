package edu.miu.cs.cs544.controller.impl;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationControllerImplementation {

    @Autowired
    ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws CustomError {

        System.out.println("Controller "+reservationDTO);
        ReservationDTO createdReservation = new ReservationDTO();
        if (reservationDTO != null){
            createdReservation = reservationService.createReservation(reservationDTO);
        }

        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);


    }
}
