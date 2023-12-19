package edu.miu.cs.cs544.service.impl;

import edu.miu.cs.cs544.domain.*;
import edu.miu.cs.cs544.domain.dto.AddressDTO;
import edu.miu.cs.cs544.domain.dto.CustomerDTO;
import edu.miu.cs.cs544.domain.dto.UserDTO;
import edu.miu.cs.cs544.repository.CustomerRepository;
import edu.miu.cs.cs544.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {



    @Autowired
    private CustomerRepository customerRepository;



    public Customer createCustomer(String firstName, String lastName, String email,
                                      Address billingAddress, Address physicalAddress,
                                      String userName, String userPass, RoleType roleType) {
        // Create User
        User user = new User();
        user.setUserName(userName);
        user.setUserPass(userPass);
        user.setRoleType(roleType);
        user.setActive(true);

        // Create Customer
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setUser(user);

        // Set billing and physical addresses
        customer.setCustomerBillingAddress(billingAddress);
        customer.setCustomerPhysicalAddress(physicalAddress);

        // Save Customer and User
       return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerId));
    }

    public Customer updateCustomer(Integer customerId, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(customerId);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            // Update fields as needed
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setCustomerBillingAddress(updatedCustomer.getCustomerBillingAddress());
            existingCustomer.setCustomerPhysicalAddress(updatedCustomer.getCustomerPhysicalAddress());

            // Save the updated customer
            return customerRepository.save(existingCustomer);
        } else {
            // Handle customer not found
            throw new RuntimeException("Customer not found with id: " + customerId);
        }
    }
    public List<Reservation> getReservationsForCustomer(Integer customerId) {
        Optional<Customer> customer = Optional.ofNullable(getCustomerById(customerId));
        return customer.orElseThrow().getReservationList();
    }


    public void deleteCustomer(Integer customerId) {
        customerRepository.deleteById(customerId);
    }

}
