package edu.miu.cs.cs544.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;
	
	private String name;

	@Embedded
	private AuditData auditData;
	
}
