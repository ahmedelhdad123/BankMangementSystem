package com.fawry.bankmangementsystem.dto;

import com.fawry.bankmangementsystem.entity.enumrole.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false)
    private Double amount;

    @Column
    private String notes;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
