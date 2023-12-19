package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws CustomError {
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable int id) throws CustomError {
        return new ResponseEntity<>(reservationService.getReservation(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable int id) throws CustomError {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable int id, @RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO updatedReservation = reservationService.updateReservation(id, reservationDTO);
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{productType}")
    public ResponseEntity<List<ReservationDTO>> getAllReservationsByProductType(@PathVariable String productType) {
        try {
            List<ReservationDTO> reservationDTOList = reservationService.getAllReservationByProductType(ProductType.valueOf(productType));
            return new ResponseEntity<>(reservationDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reservationType}")
    public ResponseEntity<List<ReservationDTO>> getAllReservationsByReservationType(@PathVariable String reservationType) {
        try {
            List<ReservationDTO> reservationDTOList = reservationService.getAllReservationByReservationType(ReservationType.valueOf(reservationType));
            return new ResponseEntity<>(reservationDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllReservations() throws CustomError {
        return new ResponseEntity<>(reservationService.getAllReservation(), HttpStatus.OK);
    }
}
