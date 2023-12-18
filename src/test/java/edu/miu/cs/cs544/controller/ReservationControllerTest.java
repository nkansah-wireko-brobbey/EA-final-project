package edu.miu.cs.cs544.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.dto.ItemDTO;
import edu.miu.cs.cs544.domain.dto.ProductDTO;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createReservation() throws Exception {
        double nightlyRate = 100;
        ProductDTO productDTO = new ProductDTO(2, "Test Product", "Test Product", "This is a test product", ProductType.Room, nightlyRate, 2, true, null);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        ItemDTO itemDTO1 = new ItemDTO(1, 5, null, null, productDTO, null);
        itemDTOList.add(itemDTO1);
        ReservationDTO reservationDTO = new ReservationDTO(13, itemDTOList, null, ReservationType.NEW);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                        .content(new ObjectMapper().writeValueAsString(reservationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        Mockito.verify(reservationService, Mockito.times(1)).createReservation(reservationDTO);

    }

    @Test
    public void deleteReservation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reservations/1")).andExpect(status().isOk());
        Mockito.verify(reservationService, Mockito.times(1)).deleteReservation(1);
    }
}