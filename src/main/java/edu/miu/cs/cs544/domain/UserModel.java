package edu.miu.cs.cs544.domain;

import edu.miu.cs.cs544.domain.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String matchingPassword;
}
