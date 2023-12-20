package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO, getEmailFromAuthentication()), HttpStatus.CREATED);
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
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody ReservationDTO reservationDTO) throws CustomError {
        return new ResponseEntity<>(reservationService.updateReservation(id, reservationDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllReservations() throws CustomError {
        return new ResponseEntity<>(reservationService.getAllReservation(), HttpStatus.OK);
    }

    private String getEmailFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = jwtAuthenticationToken.getTokenAttributes();
        return (String)attributes.get("email");
    }
}
