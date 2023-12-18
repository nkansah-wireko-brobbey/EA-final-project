package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.domain.adapter.CustomerAdapter;
import edu.miu.cs.cs544.domain.adapter.ItemAdapter;
import edu.miu.cs.cs544.domain.adapter.ProductAdapter;
import edu.miu.cs.cs544.domain.adapter.ReservationAdapter;
import edu.miu.cs.cs544.domain.dto.*;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.ProductRepository;
import edu.miu.cs.cs544.repository.ReservationRepository;
import edu.miu.cs.cs544.service.ProductService;
import edu.miu.cs.cs544.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ReservationServiceImplementationTest {

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @MockBean
    private CustomerRepository customerRepository;


    @TestConfiguration
    static class ReservationServiceImplementationTestContextConfiguration {
        @Bean
        public ReservationServiceImplementation reservationServiceImplementation(CustomerRepository customerRepository,
                                                                                 ReservationRepository reservationRepository,
                                                                                 ProductRepository productRepository){
            return new ReservationServiceImplementation(customerRepository,reservationRepository,productRepository);
        }
    }


    @Test
    void createReservation() throws CustomError {
        double nightlyRate = 100;
        Product product = new Product(1, "Test Product", "Test Product", "This is a test product", ProductType.Room, nightlyRate, 2, true, null);
        List<Item> itemList = new ArrayList<>();
        Item item = new Item(1, 5, null, null, product, null);
        itemList.add(item);
        State stateDTO = new State(1, "22342", "Iowa", null);
        Address addressDTO = new Address(1,"123 Ave", null, "Fairfield", stateDTO, "54333", null);
        Customer customer = new Customer(1,"John","Doe", "john@gmail.com", null, addressDTO, addressDTO, null, null);
//        customerDTO.setCustomerBillingAddressDTO(addressDTO);
//        customerDTO.setCustomerBillingAddressDTO(addressDTO);
//        ReservationDTO reservationDTO = new ReservationDTO(1, itemDTOList, null, ReservationType.NEW);
//
//        Mockito.when(productRepository.save(ProductAdapter.getProduct(productDTO))).thenReturn(ProductAdapter.getProduct(productDTO));
////        Mockito.when(customerRepository.save(CustomerAdapter.getCustomer(customerDTO))).thenReturn(CustomerAdapter.getCustomer(customerDTO));

        Reservation reservation = new Reservation(1,customer, itemList, null, ReservationType.NEW);
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        reservationService.createReservation(ReservationAdapter.getReservationDTO(reservation));
        Mockito.verify(reservationRepository, Mockito.times(1)).save(reservation);

    }


    @Test
    void getReservation() {
    }

    @Test
    void getAllReservation() {
    }

    @Test
    void updateReservation() {
    }

    @Test
    void deleteReservation() throws CustomError {
        double nightlyRate = 100;
        Product product = new Product(1, "Test Product", "Test Product", "This is a test product", ProductType.Room, nightlyRate, 2, true, null);
        List<Item> itemList = new ArrayList<>();
        Item item = new Item(1, 5, null, null, product, null);
        itemList.add(item);
        State stateDTO = new State(1, "22342", "Iowa", null);
        Address addressDTO = new Address(1,"123 Ave", null, "Fairfield", stateDTO, "54333", null);
        Customer customer = new Customer(1,"John","Doe", "john@gmail.com", null, addressDTO, addressDTO, null, null);
        Reservation reservation = new Reservation(1,customer, itemList, null, ReservationType.NEW);
        Optional<Reservation> optionalReservation = Optional.of(reservation);
        Mockito.when(reservationRepository.findById(reservation.getId())).thenReturn(optionalReservation);
        reservationService.deleteReservation(1);
        Mockito.verify(reservationRepository, Mockito.times(1)).delete(optionalReservation.get());

    }

    @Test
    void getAllReservationByProductType() {
    }

    @Test
    void getAllReservationByReservationType() {
    }
}