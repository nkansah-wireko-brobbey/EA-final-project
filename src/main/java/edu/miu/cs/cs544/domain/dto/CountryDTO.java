package edu.miu.cs.cs544.domain.dto;

import edu.miu.cs.cs544.domain.AuditData;
import edu.miu.cs.cs544.domain.State;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class CountryDTO {
    private Integer id;
    private String code;

    private String name;

    private Integer population;

    private AuditDataDTO auditData;

    private List<State> stateList = new ArrayList<>();
}
