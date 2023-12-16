package edu.miu.cs.cs544.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    private String excerpt;

    private ProductType type;
    private double nightlyRate;
    private int maxCapacity;

    private Boolean isAvailable;

    private AuditData auditData;
}
