package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.controller.UserAlreadyExistException;
import edu.miu.cs.cs544.domain.UserModel;
import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.domain.VerificationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User registerUser(UserModel userModel) throws UserAlreadyExistException;

    void createVerificationToken(User user, String token);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String existingToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String ValidatePasswordResetToken(String token);

    Optional<User> getUserByPasswordToken(String token);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
