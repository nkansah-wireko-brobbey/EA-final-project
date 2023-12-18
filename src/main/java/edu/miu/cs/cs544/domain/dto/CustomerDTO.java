package edu.miu.cs.cs544.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Integer id;

    @Column(nullable = false)
    private String firstName;


    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private AuditDataDTO auditDataDTO;

    private AddressDTO customerPhysicalAddressDTO;

    private AddressDTO customerBillingAddressDTO;

    private List<ReservationDTO> reservationDTOList = new ArrayList<>();

    private UserDTO userDTO;

}
