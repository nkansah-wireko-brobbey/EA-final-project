package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.State;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Embedded;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Integer id;

    private String line1;

    private String line2;

    private String city;
    private StateDTO stateDTO;

    private String postalCode;

    private AuditDataDTO auditData;
}
