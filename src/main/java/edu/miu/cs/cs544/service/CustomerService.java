package edu.miu.cs.cs544.service;

import edu.miu.cs.cs544.domain.Address;
import edu.miu.cs.cs544.domain.Customer;
import edu.miu.cs.cs544.domain.Reservation;
import edu.miu.cs.cs544.domain.RoleType;
import edu.miu.cs.cs544.domain.dto.AddressDTO;
import edu.miu.cs.cs544.domain.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    public Customer createCustomer(String firstName, String lastName, String email,
                                      Address billingAddress, Address physicalAddress,
                                      String userName, String userPass, RoleType roleType);
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(Integer customerId);
    public Customer updateCustomer(Integer customerId, Customer updatedCustomer);
    public List<Reservation> getReservationsForCustomer(Integer customerId);
    public void deleteCustomer(Integer customerId);

}
