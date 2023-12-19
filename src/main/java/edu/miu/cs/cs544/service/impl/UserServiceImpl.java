package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.domain.dto.AddressDTO;
import edu.miu.cs.cs544.domain.dto.CustomerDTO;
import edu.miu.cs.cs544.domain.dto.StateDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.repository.PasswordResetTokenRepository;
import edu.miu.cs.cs544.repository.UserRepository;
import edu.miu.cs.cs544.repository.VerificationTokenRepository;
import edu.miu.cs.cs544.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(CustomerDTO customerDTO) throws CustomError {
        User existingUser = userRepository.findByEmail(customerDTO.getEmail());
        if (existingUser != null) {
            throw new CustomError("There is an account with that email address: " + customerDTO.getEmail());
        }
        User user = new User();
        user.setUserName(customerDTO.getUserName());
        user.setRoleType(RoleType.CLIENT);
        user.setUserPass(passwordEncoder.encode(customerDTO.getUserPass()));
        userRepository.save(user);

        Customer customer = getCustomer(customerDTO, user);

        // Save the Customer entity
        customerRepository.save(customer);
        return user;
    }

    private Customer getCustomer(CustomerDTO customerDTO, User user) {
        Address physicalAddress = mapAddressDTOToEntity(customerDTO.getCustomerPhysicalAddressDTO());
        Address billingAddress = mapAddressDTOToEntity(customerDTO.getCustomerBillingAddressDTO());

        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());

        // Set Address entities in Customer
        customer.setCustomerPhysicalAddress(physicalAddress);
        customer.setCustomerBillingAddress(billingAddress);
        customer.setAuditData(new AuditData());
        customer.setUser(user);


        return customer;
    }


    private Address mapAddressDTOToEntity(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }

        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());

        StateDTO stateDTO = addressDTO.getStateDTO();
        if (stateDTO != null) {
            State state = mapStateDTOToEntity(stateDTO);
            address.setState(state);
        }

        return address;
    }

    private State mapStateDTOToEntity(StateDTO stateDTO) {
        if (stateDTO == null) {
            return null;
        }

        State state = new State();
        state.setName(stateDTO.getName());
        state.setCode(stateDTO.getCode());
        return state;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user,token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null){
            return "invalidToken";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        //Activate the user
        user.setActive(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(existingToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
     PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
     passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String ValidatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null){
            return "invalidToken";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setUserPass(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword,user.getUserPass());
    }

    @Override
    public void deleteUser(Integer id) throws CustomError {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new CustomError("User not found");
        }
        userRepository.delete(user);
    }

    @Override
    public void updateUserDetails(User user) throws CustomError {
            userRepository.save(user);
        }
}
