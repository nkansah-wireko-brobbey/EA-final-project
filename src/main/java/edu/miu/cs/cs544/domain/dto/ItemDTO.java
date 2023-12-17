package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Integer id;

    @NotNull
    private Integer occupants;

    @NotNull
    private LocalDate checkinDate;

    @NotNull
    private LocalDate checkoutDate;


    @NotNull
    private ProductDTO productDTO;

    private AuditData auditData;
}
