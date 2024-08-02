package com.fawry.bankmangementsystem.dto.mabstrauct;

import com.fawry.bankmangementsystem.dto.TransactionDTO;
import com.fawry.bankmangementsystem.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionDTO transactionDTO);

    TransactionDTO toDTO(Transaction transaction);
}
