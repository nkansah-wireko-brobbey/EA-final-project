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
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(RetryExtension.class)
public class ReservationRestTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost/api";
    }


    @Test
    public void testCreateReservation() {

        //create a test product for reservation
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setExcerpt("Test Product");
        productDTO.setDescription("This is a test product");
        productDTO.setType(ProductType.Room);
        productDTO.setMaxCapacity(2);
        productDTO.setNightlyRate(100.0);

        // Send POST request to add the product and capture the response
        Response response = given()
                .body(productDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .extract()
                .response();
        int productId = response.jsonPath().getInt("id");
        productDTO.setId(productId);

        ReservationDTO reservationDTO = new ReservationDTO();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProduct(productDTO);
        itemDTO.setOccupants(5);
        itemDTO.setCheckinDate(LocalDate.now());
        itemDTO.setCheckoutDate(LocalDate.now());
        List<ItemDTO> itemDTOList = new ArrayList<>();
        itemDTOList.add(itemDTO);
        reservationDTO.setItems(itemDTOList);
        reservationDTO.setReservationType(ReservationType.NEW);
        System.out.println(reservationDTO);

        Response response1 = given()
                .body(reservationDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/reservations")
                .then()
                .statusCode(201)
                .extract()
                .response();

        int id = response1.jsonPath().getInt("id");

        given()
                .when()
                .get("/reservations/" + id)
                .then()
                .statusCode(200)
                .body("items[0].occupants", equalTo(5))
                .body("reservationType", equalTo("NEW"));


        //clean up
        given()
                .when()
                .delete("/reservations/" + id)
                .then()
                .statusCode(200);

        // Send DELETE request to clean the new product
        given()
                .when()
                .delete("/products/" + productId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetReservations() {

    }

    @Test
    public void testGetReservationByID() {

    }
}
