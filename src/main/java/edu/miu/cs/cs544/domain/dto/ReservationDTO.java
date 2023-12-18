package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.Item;
import edu.miu.cs.cs544.domain.ReservationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Integer id;

    @NotNull
    private List<Item> items;

    private AuditData auditData;

    @NotNull
    private ReservationType reservationType;
}

