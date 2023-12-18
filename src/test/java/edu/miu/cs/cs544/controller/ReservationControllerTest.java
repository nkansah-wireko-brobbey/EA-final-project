package edu.miu.cs.cs544.controller;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.dto.AuditDataDTO;
import edu.miu.cs.cs544.domain.dto.ItemDTO;
import edu.miu.cs.cs544.domain.dto.ProductDTO;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import edu.miu.cs.cs544.service.ReservationService;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createReservation() throws Exception{
        double nightlyRate = 100;
        ProductDTO productDTO = new ProductDTO(2, "Test Product", "Test Product", "This is a test product", ProductType.Room, nightlyRate, 2, true, null);
        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now();
        ItemDTO itemDTO1 = new ItemDTO(1,5, checkInDate, checkOutDate, productDTO,  null);
        itemDTOList.add(itemDTO1);
//        ReservationDTO reservationDTO = new ReservationDTO(13, itemDTOList,null, ReservationType.NEW);
    }

    @Test
    public void getReservationTest(){

    }


}