package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.ReservationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Integer id;

    @Column(nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private List<Item> items;

    @Column(nullable = false)
    private AuditData auditData;

    @Column(nullable = false)
    private ReservationType reservationType;
}

