package edu.miu.cs.cs544.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AuditData {

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

}
