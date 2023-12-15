package edu.miu.cs.cs544.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
	private List<Item> items;

	@Embedded
	private AuditData auditData;

	@Enumerated(EnumType.STRING)
	private ReservationType reservationType;
	
}
