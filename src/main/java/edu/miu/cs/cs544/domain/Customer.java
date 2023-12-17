package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	
	private String lastName;

	private String email;
	
	private AuditData auditData;

	@ManyToOne(cascade = CascadeType.ALL)
	private Address custmerPhysicalAddress;

	@ManyToOne(cascade = CascadeType.ALL)
	private Address custmerBillingAddress;

	@OneToMany(mappedBy = "customer")
	private List<Reservation> reservationList = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
}
