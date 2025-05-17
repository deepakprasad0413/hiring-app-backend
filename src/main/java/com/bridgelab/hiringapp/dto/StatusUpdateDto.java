package com.bridgelab.hiringapp.dto;

import com.bridgelab.hiringapp.entity.Candidate;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatusUpdateDto {
    @NotNull(message = "status cant be empty")
    @Enumerated
    Candidate.Status status;
}
