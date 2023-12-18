package edu.miu.cs.cs544.cs544;

import edu.miu.cs.cs544.domain.ProductType;
import edu.miu.cs.cs544.domain.ReservationType;
import edu.miu.cs.cs544.domain.dto.ItemDTO;
import edu.miu.cs.cs544.domain.dto.ProductDTO;
import edu.miu.cs.cs544.domain.dto.ReservationDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReservationRestTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost/api";
    }


    @Test
    public void testCreateReservation() {
        ReservationDTO reservationDTO = new ReservationDTO();
        ItemDTO itemDTO = new ItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setExcerpt("Test Product");
        productDTO.setDescription("This is a test product");
        productDTO.setType(ProductType.Room);
        productDTO.setMaxCapacity(2);
        productDTO.setNightlyRate(100.0);
        itemDTO.setProduct(productDTO);
        itemDTO.setOccupants(5);
        itemDTO.setCheckinDate(null);
        itemDTO.setCheckoutDate(null);
        itemDTO.setAuditData(null);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemDTOList.add(itemDTO);
        reservationDTO.setItems(itemDTOList);
        reservationDTO.setReservationType(ReservationType.NEW);



        Response response = given()
                .body(reservationDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/reservations")
                .then()
                .statusCode(201)
                .extract()
                .response();

        int id = response.jsonPath().getInt("id");

        System.out.println("Id: " + id + " Actual Response Body: " + response.asString());

        given()
                .when()
                .get("/reservations/" + id)
                .then()
                .statusCode(200)
                .body("items[0]", equalTo(itemDTO))
                .body("auditData", equalTo(null))
                .body("reservationType", equalTo(ReservationType.NEW));



        given()
                .when()
                .delete("/reservations/" + id)
                .then()
                .statusCode(200);
    }
    @Test
    public void testGetReservations(){

    }
    @Test
    public void testGetReservationByID(){

    }
}
