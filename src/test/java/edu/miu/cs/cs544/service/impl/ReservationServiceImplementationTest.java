package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.CustomError;
import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.adapter.ReservationAdapter;
import edu.miu.cs.cs544.domain.dto.AuditDataDTO;
import edu.miu.cs.cs544.domain.dto.ItemDTO;
import edu.miu.cs.cs544.domain.dto.ProductDTO;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.repository.ReservationRepository;
import edu.miu.cs.cs544.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReservationServiceImplementationTest {

    @TestConfiguration
    static class ReservationServiceImpTestContextConfiguration{
        @Bean
        public ReservationServiceImplementation reservationServiceImplementation(){
            return new ReservationServiceImplementation();
        }
    }

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Test
    void createReservation() {
    }

    @Test
    void getReservation() throws CustomError {
        ReservationDTO reservationDTO = createDummyReservationDTO(1);
        int reservationId = ReservationAdapter.getReservation(reservationDTO).getId();
        Reservation reservation = ReservationAdapter.getReservation(reservationDTO);
        Optional<Reservation> reservationOptional = Optional.of(reservation);

        Mockito.when(reservationRepository.findById(reservationId)).thenReturn(reservationOptional);

        ReservationDTO found = reservationService.getReservation(1);
        assertEquals(found.getId(),1);
    }

    @Test
    void getAllReservation() throws CustomError {
        ReservationDTO reservationDTO = createDummyReservationDTO(1);
        ReservationDTO reservationDTO2 = createDummyReservationDTO(2);

        List<Reservation> reservationList = List.of(ReservationAdapter.getReservation(reservationDTO),ReservationAdapter.getReservation(reservationDTO2));

        Mockito.when(reservationRepository.findAll()).thenReturn(reservationList);

        List<ReservationDTO> reservationDTOList = reservationService.getAllReservation();

        assertEquals(reservationList.size(), reservationDTOList.size());

        for (int i = 0; i < reservationList.size(); i++) {

            assertEquals(reservationList.get(i).getId(), reservationDTOList.get(i).getId());
            assertEquals(reservationList.get(i).getReservationType(), reservationDTOList.get(i).getReservationType());
            assertEquals(reservationList.get(i).getItems().size(), reservationDTOList.get(i).getItems().size());
        }
    }

    @Test
    void updateReservation() {
    }

    @Test
    void deleteReservation() {
    }

    @Test
    void getAllReservationByProductType() {

    }

    @Test
    void getAllReservationByReservationType() {

    }

    private ReservationDTO createDummyReservationDTO(Integer reservationId) {
        // Create a dummy ItemDTO
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(1);
        itemDTO.setOccupants(2);
        itemDTO.setCheckinDate(LocalDate.now());
        itemDTO.setCheckoutDate(LocalDate.now().plusDays(3));

        // Create a dummy ProductDTO
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1);
        productDTO.setName("Sample Product");
        productDTO.setDescription("Sample Product Description");
        productDTO.setExcerpt("Sample Excerpt");
        productDTO.setType(ProductType.Apartment);
        productDTO.setNightlyRate(100.0);
        productDTO.setMaxCapacity(4);
        productDTO.setIsAvailable(true);

        // Set ProductDTO in ItemDTO
        itemDTO.setProduct(productDTO);

        // Create a dummy AuditDataDTO
        AuditDataDTO auditDataDTO = new AuditDataDTO();
        auditDataDTO.setCreatedBy("testUser");
        auditDataDTO.setCreatedOn(LocalDateTime.now());
        auditDataDTO.setUpdatedBy("testUser");
        auditDataDTO.setUpdatedOn(LocalDateTime.now());

        // Create a dummy ReservationDTO
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservationId);
        reservationDTO.setItems(Collections.singletonList(itemDTO));
        reservationDTO.setAuditData(auditDataDTO);
        reservationDTO.setReservationType(ReservationType.NEW);


        return reservationDTO;
    }
}