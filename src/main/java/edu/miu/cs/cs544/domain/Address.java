package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String line1;
	
	private String line2;
	
	private String city;
	@ManyToOne
	private State state;
	
	private String postalCode;

	@Embedded
	private AuditData auditData;
	
}
