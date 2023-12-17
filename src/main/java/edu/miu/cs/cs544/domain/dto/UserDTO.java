package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.RoleType;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    private String userName;

    private String userPass;

    private Boolean active;

    private AuditDataDTO auditDataDTO;

    private RoleType roleType;
}
