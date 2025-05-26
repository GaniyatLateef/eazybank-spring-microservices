package com.eazybank.accounts.service.client;

import com.eazybank.accounts.dto.CardsDto;
import com.eazybank.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="loans", url= "http://loans:8090", fallback = LoansFallBack.class)
public interface LoansFeignClient {

    @GetMapping(value="/api/loans/fetch",consumes = "application/json")
    //URL: http://localhost:8090/api/loans/fetch?mobileNumber=1234567890
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("eazybank-correlation-id") String correlationId,
                        @RequestParam String mobileNumber);
}
