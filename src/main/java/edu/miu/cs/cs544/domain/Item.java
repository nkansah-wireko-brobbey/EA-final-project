package edu.miu.cs.cs544.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer occupants;
	
	private LocalDate checkinDate;

	private LocalDate checkoutDate;

	@ManyToOne(cascade = CascadeType.ALL)
	private Reservation reservation;

	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;

	@Embedded
	private AuditData auditData;
	
}
