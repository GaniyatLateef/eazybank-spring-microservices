package com.eazybank.accounts.service.client;

import com.eazybank.accounts.dto.LoansDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public class LoansFallBack implements LoansFeignClient{

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
