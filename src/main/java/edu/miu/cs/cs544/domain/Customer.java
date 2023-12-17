package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
	private Address customerPhysicalAddress;

	@ManyToOne(cascade = CascadeType.ALL)
	private Address customerBillingAddress;

	@OneToMany(mappedBy = "customer")
	private List<Reservation> reservationList = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
}
