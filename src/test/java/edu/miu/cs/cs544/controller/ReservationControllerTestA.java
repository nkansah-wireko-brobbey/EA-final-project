package edu.miu.cs.cs544.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.dto.AuditDataDTO;
import edu.miu.cs.cs544.domain.dto.ItemDTO;
import edu.miu.cs.cs544.domain.dto.ProductDTO;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ReservationController.class)
class ReservationControllerTestA {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateReservation() throws Exception {
        ReservationDTO reservationDTO = createDummyReservationDTO();
        when(reservationService.createReservation(any(ReservationDTO.class))).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(reservationDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].occupants").value(reservationDTO.getItems().get(0).getOccupants()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditData").value(reservationDTO.getAuditData()));

    }

    @Test
    void testGetReservation() throws Exception {
        int reservationId = 1;
        ReservationDTO reservationDTO = createDummyReservationDTO();
        when(reservationService.getReservation(anyInt())).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/{id}", reservationId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(reservationDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].occupants").value(reservationDTO.getItems().get(0).getOccupants()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditData").value(reservationDTO.getAuditData()));
    }

    @Test
    void testDeleteReservation() throws Exception {
        int reservationId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservations/{id}", reservationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateReservation() throws Exception {
        int reservationId = 1;
        ReservationDTO reservationDTO = createDummyReservationDTO();
        when(reservationService.updateReservation(anyInt(), any(ReservationDTO.class))).thenReturn(reservationDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/reservations/{id}", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(reservationDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].occupants").value(reservationDTO.getItems().get(0).getOccupants()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auditData").value(reservationDTO.getAuditData()));
    }

    @Test
    void testGetAllReservations() throws Exception {
        ReservationDTO reservationDTO = createDummyReservationDTO();
        when(reservationService.getAllReservation()).thenReturn(Collections.singletonList(reservationDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(reservationDTO.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].items[0].occupants").value(reservationDTO.getItems().get(0).getOccupants()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].auditData").value(reservationDTO.getAuditData()));
    }

    @Test
    void testValidationException() throws Exception {
        ReservationDTO reservationDTO = createDummyReservationDTO();
        reservationDTO.getItems().get(0).setOccupants(null); // Set occupants to null to trigger validation exception

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(result -> assertEquals("occupants must not be null", result.getResolvedException().getMessage()))
                .andDo(print());
    }

    private ReservationDTO createDummyReservationDTO() {
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
        reservationDTO.setId(1);
        reservationDTO.setItems(Collections.singletonList(itemDTO));
        reservationDTO.setAuditData(auditDataDTO);
        reservationDTO.setReservationType(ReservationType.NEW);

        return reservationDTO;
    }

}
