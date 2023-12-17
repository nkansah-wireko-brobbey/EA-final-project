package edu.miu.cs.cs544.controller;


import edu.miu.cs.cs544.domain.User;
import edu.miu.cs.cs544.domain.UserModel;
import edu.miu.cs.cs544.domain.VerificationToken;
import edu.miu.cs.cs544.event.RegistrationCompleteEvent;
import edu.miu.cs.cs544.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, HttpServletRequest request) {
        User user = userService.registerUser(userModel);

        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "User registered successfully";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            return "User verified successfully";
        }
        return "Bad request";
    }

    @GetMapping("/resendRegistrationToken")
    public String resendRegistrationToken(@RequestParam("token") String existingToken, HttpServletRequest request) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = newToken.getUser();
        resendVerificationToken(user, newToken, applicationUrl(request));
        return "Token re-sent successfully";
    }

    @PostMapping("/resetPassword")
public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = applicationUrl(request);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, token, applicationUrl(request));
        }
        return url;
    }

    private String passwordResetTokenMail(User user, String token, String appUrl) {
        String url = appUrl + "/savePassword?id=" + user.getId() + "&token=" + token;
        //send password reset Email
        log.info("Click the link to reset your password: " + url);
        return url;
    }


    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = userService.ValidatePasswordResetToken(token);

        if(!result.equals("valid")){
            return "Bad request";
        }
        Optional<User> user = userService.getUserByPasswordToken(token);
        if(user.isPresent()){
            userService.changeUserPassword(user.get(), passwordModel.getNewPassword());
            return "Password changed successfully";
        }
        else {
            return "Invalid Token";
        }

    }

    @PostMapping("/changePassword")
    public String ChangePassword(@RequestBody PasswordModel passwordModel) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if (!userService.checkIfValidOldPassword(user,passwordModel.getOldPassword())) {
            return "Invalid Old Password";
        }
        //Save new password
     userService.changeUserPassword(user, passwordModel.getNewPassword());
        return "password changed successfully";
    }
    private void resendVerificationToken(User user, VerificationToken newToken, String appUrl) {
        String url = appUrl + "/registrationConfirm?token=" + newToken.getToken();
        //send verification Email
        log.info("Click the link to verify your account: " + url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
