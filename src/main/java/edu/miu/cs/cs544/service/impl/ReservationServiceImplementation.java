package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.domain.adapter.ProductAdapter;
import edu.miu.cs.cs544.domain.adapter.ReservationAdapter;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.repository.ReservationRepository;
import edu.miu.cs.cs544.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImplementation implements ReservationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) throws CustomError {
        //        Write logic

        Customer c = new Customer();


        Customer saved = customerRepository.save(c);
        Reservation reservation =  ReservationAdapter.getReservation(reservationDTO);
        reservation.setCustomer(saved);


        if(customerRepository.findById(reservation.getCustomer().getId()).isEmpty()){
            throw new CustomError(saved.getId() + " is not valid", HttpStatus.NOT_FOUND);
        }

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