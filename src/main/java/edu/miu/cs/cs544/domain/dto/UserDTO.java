package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.RoleType;
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
