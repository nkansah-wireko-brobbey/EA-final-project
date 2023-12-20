package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.domain.adapter.ReservationAdapter;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.repository.ReservationRepository;
import edu.miu.cs.cs544.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(force = true)
@Service
public class ReservationServiceImplementation implements ReservationService {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final ProductRepository productRepository;

    public ReservationServiceImplementation(CustomerRepository customerRepository,
                                            ReservationRepository reservationRepository,
                                            ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.productRepository = productRepository;

    }


    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO, String email) throws CustomError {
        Customer customer = customerRepository.findByEmail(email);
        if (customer==null) {
            throw new CustomError(email + " is not valid", HttpStatus.NOT_FOUND);
        }
        Reservation reservation = ReservationAdapter.getReservation(reservationDTO);
        reservation.setCustomer(customer);
        reservation.setAuditData(getAuditData(email));

        List<Item> itemList = reservation.getItems();
        for (Item item : itemList) {
            Optional<Product> availableItem = productRepository.findById(item.getProduct().getId());
            if (availableItem.isPresent()) {
                if (!availableItem.get().getIsAvailable()) {
                    throw new CustomError(availableItem.get().getName() + " is not available");
                }
                availableItem.get().setIsAvailable(false);
                productRepository.save(availableItem.get());
            }

        }

        return ReservationAdapter.getReservationDTO(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDTO getReservation(int id) throws CustomError {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent())
            return ReservationAdapter.getReservationDTO(reservation.get());

        throw new CustomError("Reservation with ID: " + id + " not found");

    }

    @Override
    @Transactional
    public List<ReservationDTO> getAllReservation() throws CustomError {
        List<ReservationDTO> reservationDTOList = reservationRepository
                .findAll()
                .stream()
                .map(ReservationAdapter::getReservationDTO)
                .collect(
                        Collectors
                                .toList()
                );
        return reservationDTOList;
    }

    @Override
    public ReservationDTO updateReservation(int id, ReservationDTO reservationDTO) throws CustomError {
        //        Write logic
        return null;
    }

    @Override
    public void deleteReservation(int id) throws CustomError {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.delete(reservation.get());
        } else {
            throw new CustomError("Reservation with ID: " + id + " not found");
        }
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

    private AuditData getAuditData(String email) {
        AuditData auditData = new AuditData();
        auditData.setCreatedBy(email);
        auditData.setUpdatedOn(LocalDateTime.now());
        auditData.setCreatedOn(LocalDateTime.now());
        auditData.setUpdatedBy(email);
        return auditData;
    }
}
